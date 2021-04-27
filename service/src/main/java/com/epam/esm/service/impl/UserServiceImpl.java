package com.epam.esm.service.impl;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Orders;
import com.epam.esm.dao.entity.User;
import com.epam.esm.dao.repository.GiftCertificateRepository;
import com.epam.esm.dao.repository.OrdersRepository;
import com.epam.esm.dao.repository.TagRepository;
import com.epam.esm.dao.repository.UserRepository;
import com.epam.esm.dao.specification.gift.GetGiftCertificatesByIdSpecification;
import com.epam.esm.dao.specification.tag.GetMostWidelyUsedTagSpecification;
import com.epam.esm.dao.specification.user.GetAllUsersSpecification;
import com.epam.esm.dao.specification.user.GetUserByIdSpecification;
import com.epam.esm.service.UserService;
import com.epam.esm.service.builder.certificate.GiftCertificateDtoBuilder;
import com.epam.esm.service.builder.order.OrdersBuilder;
import com.epam.esm.service.builder.order.OrdersDtoBuilder;
import com.epam.esm.service.builder.tag.TagDtoBuilder;
import com.epam.esm.service.builder.user.UserDtoBuilder;
import com.epam.esm.service.dto.OrdersDto;
import com.epam.esm.service.dto.PageDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.exception.EmptyOrderException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.InsufficientFundInAccount;
import com.epam.esm.service.exception.PageNotValidException;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.validator.PageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final OrdersRepository ordersRepository;
  private final GiftCertificateRepository giftCertificateRepository;
  private final UserDtoBuilder userDtoBuilder;
  private final OrdersBuilder ordersBuilder;
  private final GiftCertificateDtoBuilder giftCertificateDtoBuilder;
  private final OrdersDtoBuilder ordersDtoBuilder;
  private final TagDtoBuilder tagDtoBuilder;
  private final TagRepository tagRepository;
  private final PageValidator pageValidator;
  private static final int FIST_ELEMENT = 0;

  @Autowired
  public UserServiceImpl(
      UserRepository userRepository,
      OrdersRepository ordersRepository,
      GiftCertificateRepository giftCertificateRepository,
      UserDtoBuilder userDtoBuilder,
      OrdersBuilder ordersBuilder,
      GiftCertificateDtoBuilder giftCertificateDtoBuilder,
      OrdersDtoBuilder ordersDtoBuilder,
      TagDtoBuilder tagDtoBuilder,
      TagRepository tagRepository,
      PageValidator pageValidator) {
    this.userRepository = userRepository;
    this.ordersRepository = ordersRepository;
    this.giftCertificateRepository = giftCertificateRepository;
    this.userDtoBuilder = userDtoBuilder;
    this.ordersBuilder = ordersBuilder;
    this.giftCertificateDtoBuilder = giftCertificateDtoBuilder;
    this.ordersDtoBuilder = ordersDtoBuilder;
    this.tagDtoBuilder = tagDtoBuilder;
    this.tagRepository = tagRepository;
    this.pageValidator = pageValidator;
  }

  @Override
  public List<UserDto> getAll(Integer page, Integer size) {
    if (!pageValidator.isValid(new PageDto(page, size))) {
      throw new PageNotValidException(pageValidator.getErrorMessage());
    }
    return userRepository
        .getEntityListWithPaginationBySpecification(new GetAllUsersSpecification(), page, size)
        .stream()
        .map((userDtoBuilder::build))
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public Optional<OrdersDto> makeOrder(Long id, List<Long> giftCertificateDtoIds) {
    User user =
        userRepository
            .getEntityBySpecification(new GetUserByIdSpecification(id))
            .orElseThrow(
                () ->
                    new EntityNotFoundException("Requested resource not found (id = " + id + ")"));

    if (giftCertificateDtoIds == null || giftCertificateDtoIds.isEmpty()) {
      throw new EmptyOrderException("You can't make empty order");
    }
    List<GiftCertificate> giftCertificates = getAllGiftCertificates(giftCertificateDtoIds);
    BigDecimal cost = getNewUserAccount(user, giftCertificates);
    userDtoBuilder.build(
        userRepository
            .update(user)
            .orElseThrow(() -> new EntityNotFoundException("The User not exists in the DB")));

    OrdersDto ordersDto = createOrderDto(user.getId(), cost, giftCertificates);
    Orders orders =
        ordersRepository
            .save(ordersBuilder.build(ordersDto, user))
            .orElseThrow(() -> new ServiceException("The Orders wasn't saved"));
    return Optional.of(ordersDtoBuilder.build(orders));
  }

  private BigDecimal getNewUserAccount(User user, List<GiftCertificate> giftCertificates) {
    BigDecimal cost =
        giftCertificates.stream()
            .map(GiftCertificate::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal account = user.getAccount();
    if (account.compareTo(cost) < 0) {
      throw new InsufficientFundInAccount("The user doesn't have enough funds in the account");
    }
    user.setAccount(account.subtract(cost));
    return cost;
  }

  private List<GiftCertificate> getAllGiftCertificates(List<Long> giftCertificateDtoIds) {
    List<GiftCertificate> giftCertificates = new ArrayList<>();
    for (Long giftCertificateId : giftCertificateDtoIds) {
      GiftCertificate giftCertificate =
          giftCertificateRepository
              .getEntityBySpecification(new GetGiftCertificatesByIdSpecification(giftCertificateId))
              .orElseThrow(
                  () ->
                      new EntityNotFoundException(
                          "Requested resource not found (id = " + giftCertificateId + ")"));
      giftCertificates.add(giftCertificate);
    }
    return giftCertificates;
  }

  private OrdersDto createOrderDto(
      long userId, BigDecimal cost, List<GiftCertificate> giftCertificates) {
    OrdersDto ordersDto = new OrdersDto();
    ordersDto.setUserId(userId);
    ordersDto.setCost(cost);
    ordersDto.setGiftCertificates(
        giftCertificates.stream()
            .map(giftCertificateDtoBuilder::build)
            .collect(Collectors.toList()));
    return ordersDto;
  }

  @Override
  public Optional<UserDto> getById(long id) {
    return userRepository
        .getEntityBySpecification(new GetUserByIdSpecification(id))
        .map(userDtoBuilder::build);
  }

  @Override
  public Optional<TagDto> getMostWidelyUsedTagByUserId(Long id) {
    List<TagDto> tagDto =
        tagRepository.getEntityListBySpecification(new GetMostWidelyUsedTagSpecification(id))
            .stream()
            .map(tagDtoBuilder::build)
            .collect(Collectors.toList());
    return getMostWidelyTagFromList(tagDto);
  }

  private Optional<TagDto> getMostWidelyTagFromList(List<TagDto> tagDtos) {
    if (!tagDtos.isEmpty()) {
      return Optional.of(tagDtos.get(FIST_ELEMENT));
    } else {
      return Optional.empty();
    }
  }
}
