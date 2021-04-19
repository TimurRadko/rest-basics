package com.epam.esm.service.impl;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Order;
import com.epam.esm.dao.repository.GiftCertificateRepository;
import com.epam.esm.dao.repository.OrderRepository;
import com.epam.esm.dao.specification.gift.GetGiftCertificatesByOrderIdSpecification;
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
  public Optional<Order> getById(long id) {
    return Optional.empty();
  }

  @Override
  public List<OrderDto> getAllOrdersByUserId(long id) {
    List<Order> orders = orderRepository.getOrdersByUserId(id);
    return orders.stream()
        .map(order -> new OrderDto(order, getAllGiftCertificateByOrderId(order.getId())))
        .collect(Collectors.toList());
  }

  private List<GiftCertificate> getAllGiftCertificateByOrderId(long id) {
    return giftCertificateRepository.getGiftCertificatesBySpecification(
        new GetGiftCertificatesByOrderIdSpecification(id));
  }
}
