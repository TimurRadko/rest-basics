package com.epam.esm.service.impl;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Order;
import com.epam.esm.dao.entity.User;
import com.epam.esm.dao.repository.GiftCertificateRepository;
import com.epam.esm.dao.repository.OrderRepository;
import com.epam.esm.dao.repository.UserRepository;
import com.epam.esm.dao.specification.order.GetAllOrdersByUserIdSpecification;
import com.epam.esm.dao.specification.user.GetAllUsersSpecification;
import com.epam.esm.dao.specification.user.GetUserByIdSpecification;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
  private UserRepository userRepository;
  private OrderRepository orderRepository;
  private GiftCertificateRepository giftCertificateRepository;

  @Autowired
  public UserServiceImpl(
      UserRepository userRepository,
      OrderRepository orderRepository,
      GiftCertificateRepository giftCertificateRepository) {
    this.userRepository = userRepository;
    this.orderRepository = orderRepository;
    this.giftCertificateRepository = giftCertificateRepository;
  }

  @Override
  public List<UserDto> getAll() {
    return userRepository.getEntityListBySpecification(new GetAllUsersSpecification()).stream()
        .map((this::createUserDto))
        .collect(Collectors.toList());
  }

  @Override
  public Optional<UserDto> getById(long id) {
    return userRepository
        .getEntityBySpecification(new GetUserByIdSpecification(id))
        .map(this::createUserDto);
  }

  private UserDto createUserDto(User user) {
    long id = user.getId();
    List<Order> orders =
        orderRepository.getEntityListBySpecification(new GetAllOrdersByUserIdSpecification(id));
    return new UserDto(
        user,
        orders.stream()
            .map(order -> new OrderDto(order, getAllGiftCertificateByOrderId(order.getId())))
            .collect(Collectors.toList()));
  }

  //TODO: Write this method
  private List<GiftCertificate> getAllGiftCertificateByOrderId(long id) {
    return null;
  }

  @Override
  public Optional<UserDto> makeOrder(Long id, List<GiftCertificateDto> giftCertificateDtos) {
    User user =
        userRepository
            .getEntityBySpecification(new GetUserByIdSpecification(id))
            .orElseThrow(
                () ->
                    new EntityNotFoundException("Requested resource not found (id = " + id + ")"));

    //TODO; Write this method
    BigDecimal resultPrice = new BigDecimal(0);
    for (GiftCertificateDto giftCertificateDto : giftCertificateDtos) {
      BigDecimal price = giftCertificateDto.getPrice();
      resultPrice = resultPrice.add(price);
    }
    BigDecimal userAccount = user.getAccount();

    return Optional.empty();
  }

  @Override
  public Optional<UserDto> save(UserDto userDto) {
    return Optional.empty();
  }

  @Override
  public int delete(long id) {
    return 0;
  }
}
