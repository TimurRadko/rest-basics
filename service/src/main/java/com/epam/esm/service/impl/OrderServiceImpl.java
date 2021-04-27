package com.epam.esm.service.impl;

import com.epam.esm.dao.entity.Orders;
import com.epam.esm.dao.repository.OrdersRepository;
import com.epam.esm.dao.specification.order.GetAllOrdersByUserIdSpecification;
import com.epam.esm.dao.specification.order.GetOrderByIdSpecification;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.builder.order.OrdersDtoBuilder;
import com.epam.esm.service.dto.OrdersDto;
import com.epam.esm.service.dto.PageDto;
import com.epam.esm.service.exception.PageNotValidException;
import com.epam.esm.service.validator.PageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
  private final OrdersRepository ordersRepository;
  private final OrdersDtoBuilder ordersDtoBuilder;
  private final PageValidator pageValidator;

  @Autowired
  public OrderServiceImpl(
      OrdersRepository ordersRepository,
      OrdersDtoBuilder ordersDtoBuilder,
      PageValidator pageValidator) {
    this.ordersRepository = ordersRepository;
    this.ordersDtoBuilder = ordersDtoBuilder;
    this.pageValidator = pageValidator;
  }

  @Override
  public Optional<OrdersDto> getById(long id) {
    return ordersRepository
        .getEntityBySpecification(new GetOrderByIdSpecification(id))
        .map(ordersDtoBuilder::build);
  }

  @Override
  public List<OrdersDto> getAllOrdersByUserId(int page, int size, long id) {
    if (!pageValidator.isValid(new PageDto(page, size))) {
      throw new PageNotValidException(pageValidator.getErrorMessage());
    }
    List<Orders> orders =
        ordersRepository.getEntityListWithPaginationBySpecification(
            new GetAllOrdersByUserIdSpecification(id), page, size);
    return orders.stream().map(ordersDtoBuilder::build).collect(Collectors.toList());
  }
}
