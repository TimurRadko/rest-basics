package com.epam.esm.service.impl;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Orders;
import com.epam.esm.dao.entity.Users;
import com.epam.esm.dao.repository.GiftCertificateRepository;
import com.epam.esm.dao.repository.OrdersRepository;
import com.epam.esm.dao.repository.TagRepository;
import com.epam.esm.dao.repository.UserRepository;
import com.epam.esm.dao.specification.gift.GetGiftCertificatesByIdSpecification;
import com.epam.esm.dao.specification.tag.GetMostWidelyUsedTagSpecification;
import com.epam.esm.dao.specification.user.GetAllUsersSpecification;
import com.epam.esm.dao.specification.user.GetUserByIdSpecification;
import com.epam.esm.dao.specification.user.GetUserByLoginSpecification;
import com.epam.esm.service.UserService;
import com.epam.esm.service.builder.certificate.GiftCertificateDtoBuilder;
import com.epam.esm.service.builder.order.OrdersBuilder;
import com.epam.esm.service.builder.order.OrdersDtoBuilder;
import com.epam.esm.service.builder.tag.TagDtoBuilder;
import com.epam.esm.service.builder.user.UserBuilder;
import com.epam.esm.service.builder.user.UserDetailsBuilder;
import com.epam.esm.service.builder.user.UserDtoBuilder;
import com.epam.esm.service.dto.GiftCertificateDtoIds;
import com.epam.esm.service.dto.OrdersDto;
import com.epam.esm.service.dto.PageDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.UsersCreatingDto;
import com.epam.esm.service.dto.UsersDto;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.EntityNotSavedException;
import com.epam.esm.service.exception.EntityNotValidMultipleException;
import com.epam.esm.service.exception.PageNotValidException;
import com.epam.esm.service.exception.order.EmptyOrderException;
import com.epam.esm.service.exception.order.InsufficientFundInAccount;
import com.epam.esm.service.exception.user.UserLoginExistsException;
import com.epam.esm.service.locale.TranslatorLocale;
import com.epam.esm.service.validator.PageValidator;
import com.epam.esm.service.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("userServiceImpl")
public class UsersServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final TagRepository tagRepository;
  private final OrdersRepository ordersRepository;
  private final GiftCertificateRepository giftCertificateRepository;
  private final UserDtoBuilder userDtoBuilder;
  private final OrdersBuilder ordersBuilder;
  private final GiftCertificateDtoBuilder giftCertificateDtoBuilder;
  private final OrdersDtoBuilder ordersDtoBuilder;
  private final UserBuilder userBuilder;
  private final UserDetailsBuilder userDetailsBuilder;
  private final TagDtoBuilder tagDtoBuilder;
  private final PageValidator pageValidator;
  private final UserValidator userValidator;
  private final TranslatorLocale translatorLocale;

  @Autowired
  public UsersServiceImpl(
      UserRepository userRepository,
      TagRepository tagRepository,
      OrdersRepository ordersRepository,
      GiftCertificateRepository giftCertificateRepository,
      UserDtoBuilder userDtoBuilder,
      OrdersBuilder ordersBuilder,
      GiftCertificateDtoBuilder giftCertificateDtoBuilder,
      OrdersDtoBuilder ordersDtoBuilder,
      UserBuilder userBuilder,
      UserDetailsBuilder userDetailsBuilder,
      TagDtoBuilder tagDtoBuilder,
      PageValidator pageValidator,
      UserValidator userValidator,
      TranslatorLocale translatorLocale) {
    this.userRepository = userRepository;
    this.tagRepository = tagRepository;
    this.ordersRepository = ordersRepository;
    this.giftCertificateRepository = giftCertificateRepository;
    this.userDtoBuilder = userDtoBuilder;
    this.ordersBuilder = ordersBuilder;
    this.giftCertificateDtoBuilder = giftCertificateDtoBuilder;
    this.ordersDtoBuilder = ordersDtoBuilder;
    this.userBuilder = userBuilder;
    this.userDetailsBuilder = userDetailsBuilder;
    this.tagDtoBuilder = tagDtoBuilder;
    this.pageValidator = pageValidator;
    this.userValidator = userValidator;
    this.translatorLocale = translatorLocale;
  }

  @Override
  public List<UsersDto> getAll(Integer page, Integer size) {
    if (!pageValidator.isValid(new PageDto(page, size))) {
      throw new PageNotValidException(pageValidator.getErrorMessage());
    }
    return userRepository
        .getEntityListWithPagination(new GetAllUsersSpecification(), page, size)
        .stream()
        .map((userDtoBuilder::build))
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public Optional<OrdersDto> makeOrder(Long id, GiftCertificateDtoIds giftCertificateDtoIds) {
    Users users =
        userRepository
            .getEntity(new GetUserByIdSpecification(id))
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        String.format(translatorLocale.toLocale("exception.message.40401"), id)));

    List<Long> giftCertificateIds = giftCertificateDtoIds.getGiftCertificateDtoIds();
    if (giftCertificateIds == null || giftCertificateIds.isEmpty()) {
      throw new EmptyOrderException(translatorLocale.toLocale("exception.message.40006"));
    }
    List<GiftCertificate> giftCertificates = getAllGiftCertificates(giftCertificateIds);
    BigDecimal cost = getNewUserAccount(users, giftCertificates);
    userDtoBuilder.build(
        userRepository
            .update(users)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        String.format(translatorLocale.toLocale("exception.message.40401"), id))));

    OrdersDto ordersDto = createOrderDto(users.getId(), cost, giftCertificates);
    Orders orders =
        ordersRepository
            .save(ordersBuilder.build(ordersDto, users))
            .orElseThrow(
                () ->
                    new EntityNotSavedException(
                        translatorLocale.toLocale("exception.message.40011")));
    return Optional.of(ordersDtoBuilder.build(orders));
  }

  private BigDecimal getNewUserAccount(Users users, List<GiftCertificate> giftCertificates) {
    BigDecimal cost =
        giftCertificates.stream()
            .map(GiftCertificate::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal account = users.getBalance();
    if (account.compareTo(cost) < 0) {
      throw new InsufficientFundInAccount(translatorLocale.toLocale("exception.message.40005"));
    }
    users.setBalance(account.subtract(cost));
    return cost;
  }

  private List<GiftCertificate> getAllGiftCertificates(List<Long> giftCertificateDtoIds) {
    List<GiftCertificate> giftCertificates = new ArrayList<>();
    for (Long giftCertificateId : giftCertificateDtoIds) {
      GiftCertificate giftCertificate =
          giftCertificateRepository
              .getEntity(new GetGiftCertificatesByIdSpecification(giftCertificateId))
              .orElseThrow(
                  () ->
                      new EntityNotFoundException(
                          String.format(
                              translatorLocale.toLocale("exception.message.40401"),
                              giftCertificateId)));
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
  public Optional<UsersDto> getById(long id) {
    return userRepository.getEntity(new GetUserByIdSpecification(id)).map(userDtoBuilder::build);
  }

  @Override
  public Optional<TagDto> getMostWidelyUsedTagByUserId(Long id) {
    return tagRepository
        .getEntity(new GetMostWidelyUsedTagSpecification(id))
        .map(tagDtoBuilder::build);
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
    Users findingUsers =
        userRepository
            .getEntity(new GetUserByLoginSpecification(login))
            .orElseThrow(
                () ->
                    new UsernameNotFoundException(
                        (String.format(
                            translatorLocale.toLocale("exception.message.40012"), login))));
    return userDetailsBuilder.build(findingUsers);
  }

  @Override
  @Transactional
  public Optional<UsersDto> save(UsersCreatingDto userDto) {
    if (!userValidator.isValid(userDto)) {
      throw new EntityNotValidMultipleException(userValidator.getErrorMessage());
    }
    Optional<Users> existingUser =
        userRepository.getEntity(new GetUserByLoginSpecification(userDto.getLogin()));
    if (existingUser.isEmpty()) {
      Users savedUser =
          userRepository
              .save(userBuilder.buildForSave(userDto))
              .orElseThrow(
                  () ->
                      new EntityNotSavedException(
                          translatorLocale.toLocale("exception.message.40011")));
      return Optional.of(userDtoBuilder.build(savedUser));
    } else {
      throw new UserLoginExistsException(
          (String.format(
              translatorLocale.toLocale("exception.message.40902"), userDto.getLogin())));
    }
  }
}
