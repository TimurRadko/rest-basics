package com.epam.esm.service.impl;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Order;
import com.epam.esm.dao.repository.GiftCertificateRepository;
import com.epam.esm.dao.repository.OrderRepository;
import com.epam.esm.dao.specification.gift.GetGiftCertificateByIdSpecification;
import com.epam.esm.dao.specification.order.GetAllOrdersByUserIdSpecification;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
  private OrderRepository orderRepository;
  private GiftCertificateRepository giftCertificateRepository;

  @Autowired
  public OrderServiceImpl(
      OrderRepository orderRepository, GiftCertificateRepository giftCertificateRepository) {
    this.orderRepository = orderRepository;
    this.giftCertificateRepository = giftCertificateRepository;
  }

  @Override
  public Optional<Order> save(Order order) {
    return Optional.empty();
  }

  @Override
  public Optional<Order> getById(long id) {
    return Optional.empty();
  }

  @Override
  public int delete(long id) {
    return 0;
  }

  @Override
  public List<OrderDto> getAllOrdersByUserId(long id) {
    List<Order> orders =
        orderRepository.getEntityListBySpecification(new GetAllOrdersByUserIdSpecification(id));
    return orders.stream()
        .map(order -> new OrderDto(order, getGiftCertificatesByOrderId(order)))
        .collect(Collectors.toList());
  }

  private List<GiftCertificate> getGiftCertificatesByOrderId(Order order) {
    long giftCertificateId = order.getGiftCertificateId();
    return giftCertificateRepository.getEntityListBySpecification(
        new GetGiftCertificateByIdSpecification(giftCertificateId));
  }
}
