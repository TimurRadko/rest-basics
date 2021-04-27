package com.epam.esm.service.impl;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Order;
import com.epam.esm.dao.entity.User;
import com.epam.esm.dao.repository.GiftCertificateRepository;
import com.epam.esm.dao.repository.OrderRepository;
import com.epam.esm.dao.repository.TagRepository;
import com.epam.esm.dao.repository.UserRepository;
import com.epam.esm.dao.specification.gift.GetGiftCertificatesBySeveralIdsSpecification;
import com.epam.esm.dao.specification.tag.GetMostWidelyUsedTagSpecification;
import com.epam.esm.dao.specification.user.GetAllUsersSpecification;
import com.epam.esm.dao.specification.user.GetUserByIdSpecification;
import com.epam.esm.service.UserService;
import com.epam.esm.service.builder.certificate.GiftCertificateDtoBuilder;
import com.epam.esm.service.builder.order.OrderBuilder;
import com.epam.esm.service.builder.order.OrderDtoBuilder;
import com.epam.esm.service.builder.tag.TagDtoBuilder;
import com.epam.esm.service.builder.user.UserDtoBuilder;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.exception.EmptyOrderException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.InsufficientFundInAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final OrderRepository orderRepository;
  private final GiftCertificateRepository giftCertificateRepository;
  private final UserDtoBuilder userDtoBuilder;
  private final OrderBuilder orderBuilder;
  private final GiftCertificateDtoBuilder giftCertificateDtoBuilder;
  private final OrderDtoBuilder orderDtoBuilder;
  private final TagDtoBuilder tagDtoBuilder;
  private final TagRepository tagRepository;
  private static final int FIST_ELEMENT = 0;

  @Autowired
  public UserServiceImpl(
      UserRepository userRepository,
      OrderRepository orderRepository,
      GiftCertificateRepository giftCertificateRepository,
      UserDtoBuilder userDtoBuilder,
      OrderBuilder orderBuilder,
      GiftCertificateDtoBuilder giftCertificateDtoBuilder,
      OrderDtoBuilder orderDtoBuilder,
      TagDtoBuilder tagDtoBuilder,
      TagRepository tagRepository) {
    this.userRepository = userRepository;
    this.orderRepository = orderRepository;
    this.giftCertificateRepository = giftCertificateRepository;
    this.userDtoBuilder = userDtoBuilder;
    this.orderBuilder = orderBuilder;
    this.giftCertificateDtoBuilder = giftCertificateDtoBuilder;
    this.orderDtoBuilder = orderDtoBuilder;
    this.tagDtoBuilder = tagDtoBuilder;
    this.tagRepository = tagRepository;
  }

  @Override
  public List<UserDto> getAll(Integer page, Integer size) {
    return userRepository
        .getEntityListWithPaginationBySpecification(new GetAllUsersSpecification(), page, size)
        .stream()
        .map((userDtoBuilder::build))
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public Optional<OrderDto> makeOrder(Long id, List<Long> giftCertificateDtoIds) {
    User user =
        userRepository
            .getEntityBySpecification(new GetUserByIdSpecification(id))
            .orElseThrow(
                () ->
                    new EntityNotFoundException("Requested resource not found (id = " + id + ")"));

    if (giftCertificateDtoIds == null || giftCertificateDtoIds.isEmpty()) {
      throw new EmptyOrderException("You can't make empty order");
    }

    List<GiftCertificate> giftCertificates =
        giftCertificateRepository.getEntityListBySpecification(
            new GetGiftCertificatesBySeveralIdsSpecification(giftCertificateDtoIds));

    BigDecimal cost =
        giftCertificates.stream()
            .map(GiftCertificate::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal account = user.getAccount();
    if (account.compareTo(cost) < 0) {
      throw new InsufficientFundInAccount("The user doesn't have enough funds in the account");
    }
    user.setAccount(account.subtract(cost));
    userDtoBuilder.build(
        userRepository
            .update(user)
            .orElseThrow(() -> new EntityNotFoundException("The User not exists in the DB")));
    OrderDto orderDto = createOrderDto(user.getId(), cost, Set.copyOf(giftCertificates));
    Order order = orderRepository.save(orderBuilder.build(orderDto, user)).orElseThrow();

    return Optional.of(orderDtoBuilder.build(order));
  }

  private OrderDto createOrderDto(
      long userId, BigDecimal cost, Set<GiftCertificate> giftCertificates) {
    OrderDto orderDto = new OrderDto();
    orderDto.setUserId(userId);
    orderDto.setCost(cost);
    orderDto.setGiftCertificates(
        giftCertificates.stream()
            .map(giftCertificateDtoBuilder::build)
            .collect(Collectors.toSet()));
    return orderDto;
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
