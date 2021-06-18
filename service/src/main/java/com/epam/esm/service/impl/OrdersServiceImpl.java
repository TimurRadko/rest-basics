package com.epam.esm.service.impl;

import com.epam.esm.dao.entity.Orders;
import com.epam.esm.dao.repository.OrdersRepository;
import com.epam.esm.dao.specification.order.GetAllOrdersByUserIdSpecification;
import com.epam.esm.dao.specification.order.GetOrderByIdSpecification;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.builder.order.OrdersDtoBuilder;
import com.epam.esm.service.dto.OrdersDto;
import com.epam.esm.service.dto.PageDto;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.PageNotValidException;
import com.epam.esm.service.exception.user.UserDoesNotHaveOrderException;
import com.epam.esm.service.locale.LocaleTranslator;
import com.epam.esm.service.validator.PageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrdersServiceImpl implements OrderService {
  private final OrdersRepository ordersRepository;
  private final OrdersDtoBuilder ordersDtoBuilder;
  private final PageValidator pageValidator;
  private final LocaleTranslator localeTranslator;

  @Autowired
  public OrdersServiceImpl(
          OrdersRepository ordersRepository,
          OrdersDtoBuilder ordersDtoBuilder,
          PageValidator pageValidator,
          LocaleTranslator localeTranslator) {
    this.ordersRepository = ordersRepository;
    this.ordersDtoBuilder = ordersDtoBuilder;
    this.pageValidator = pageValidator;
    this.localeTranslator = localeTranslator;
  }

  @Override
  public Optional<OrdersDto> getByUserAndOrderId(long userId, long orderId) {
    List<Orders> existingOrders =
        ordersRepository.getEntityList(new GetAllOrdersByUserIdSpecification(userId));
    Orders order =
        ordersRepository
            .getEntity(new GetOrderByIdSpecification(orderId))
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        String.format(
                            localeTranslator.toLocale("exception.message.40401"), orderId)));
    if (existingOrders.isEmpty() || !existingOrders.contains(order)) {
      throw new UserDoesNotHaveOrderException(
          String.format(localeTranslator.toLocale("exception.message.40009"), userId, orderId));
    }
    return Optional.of(ordersDtoBuilder.build(order));
  }

  @Override
  public List<OrdersDto> getAllOrdersByUserId(Integer page, Integer size, long id) {
    if (!pageValidator.isValid(new PageDto(page, size))) {
      throw new PageNotValidException(pageValidator.getErrorMessage());
    }
    List<Orders> orders =
        ordersRepository.getEntityListWithPagination(
            new GetAllOrdersByUserIdSpecification(id), page, size);
    return orders.stream().map(ordersDtoBuilder::build).collect(Collectors.toList());
  }

  @Override
  public Optional<OrdersDto> getById(long id) {
    return ordersRepository
        .getEntity(new GetOrderByIdSpecification(id))
        .map(ordersDtoBuilder::build);
  }
}
