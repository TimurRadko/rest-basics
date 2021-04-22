package com.epam.esm.service.impl;

import com.epam.esm.dao.entity.Order;
import com.epam.esm.dao.repository.OrderRepository;
import com.epam.esm.dao.specification.order.GetAllOrdersByUserIdSpecification;
import com.epam.esm.dao.specification.order.GetOrderByIdSpecification;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.builder.order.OrderDtoBuilder;
import com.epam.esm.service.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
  private final OrderRepository orderRepository;
  private final OrderDtoBuilder orderDtoBuilder;

  @Autowired
  public OrderServiceImpl(OrderRepository orderRepository, OrderDtoBuilder orderDtoBuilder) {
    this.orderRepository = orderRepository;
    this.orderDtoBuilder = orderDtoBuilder;
  }

  @Override
  public Optional<OrderDto> getById(long id) {
    return orderRepository
        .getEntityBySpecification(new GetOrderByIdSpecification(id))
        .map(orderDtoBuilder::build);
  }

  @Override
  public List<OrderDto> getAllOrdersByUserId(long id) {
    List<Order> orders =
        orderRepository.getEntityListBySpecification(new GetAllOrdersByUserIdSpecification(id));
    return orders.stream().map(orderDtoBuilder::build).collect(Collectors.toList());
  }
}
