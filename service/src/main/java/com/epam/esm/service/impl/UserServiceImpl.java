package com.epam.esm.service.impl;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.User;
import com.epam.esm.dao.repository.GiftCertificateRepository;
import com.epam.esm.dao.repository.OrderRepository;
import com.epam.esm.dao.repository.UserRepository;
import com.epam.esm.dao.specification.gift.GetAllGiftCertificatesSpecification;
import com.epam.esm.dao.specification.user.GetAllUsersSpecification;
import com.epam.esm.dao.specification.user.GetUserByIdSpecification;
import com.epam.esm.service.UserService;
import com.epam.esm.service.builder.certificate.GiftCertificateDtoBuilder;
import com.epam.esm.service.builder.order.OrderBuilder;
import com.epam.esm.service.builder.user.UserDtoBuilder;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.UserDto;
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

  @Autowired
  public UserServiceImpl(
      UserRepository userRepository,
      OrderRepository orderRepository,
      GiftCertificateRepository giftCertificateRepository,
      UserDtoBuilder userDtoBuilder,
      OrderBuilder orderBuilder,
      GiftCertificateDtoBuilder giftCertificateDtoBuilder) {
    this.userRepository = userRepository;
    this.orderRepository = orderRepository;
    this.giftCertificateRepository = giftCertificateRepository;
    this.userDtoBuilder = userDtoBuilder;
    this.orderBuilder = orderBuilder;
    this.giftCertificateDtoBuilder = giftCertificateDtoBuilder;
  }

  @Override
  public List<UserDto> getAll() {
    return userRepository.getEntityListBySpecification(new GetAllUsersSpecification()).stream()
        .map((userDtoBuilder::build))
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public Optional<UserDto> makeOrder(
      Long id, Set<GiftCertificateDto> giftCertificateDtos, String sort) {
    User user =
        userRepository
            .getEntityBySpecification(new GetUserByIdSpecification(id))
            .orElseThrow(
                () ->
                    new EntityNotFoundException("Requested resource not found (id = " + id + ")"));
    List<GiftCertificate> giftCertificates =
        giftCertificateRepository.getEntityListBySpecification(
            new GetAllGiftCertificatesSpecification(sort));
    BigDecimal cost =
        giftCertificates.stream()
            .map(GiftCertificate::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal account = user.getAccount();
    if (account.compareTo(cost) < 0) {
      throw new InsufficientFundInAccount("The user doesn't have enough funds in the account");
    }
    user.setAccount(account.subtract(cost));
    OrderDto orderDto = createOrderDto(user.getId(), cost, Set.copyOf(giftCertificates));
    orderRepository.save(orderBuilder.build(orderDto));

    return Optional.of(
        userDtoBuilder.build(
            userRepository
                .update(user)
                .orElseThrow(() -> new EntityNotFoundException("The User not exists in the DB"))));
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
}
