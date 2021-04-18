package com.epam.esm.service;

import com.epam.esm.dao.entity.Order;
import com.epam.esm.service.dto.OrderDto;

import java.util.List;

/** * This interface describes specific realization of CRUD operation on Order Entities */
public interface OrderService extends Service<Order> {
  /**
   * * This method describes a general getAll (getting a list of all OrderDto) operation for all
   * OrderDtos, from persistence layer
   *
   * @param id - passed into the method id Entity's parameter that required for work with the DB
   * @return List<OrderDto> - List of OrderDto contained in one of all tables in the DB
   */
  List<OrderDto> getAllOrdersByUserId(long id);
}
