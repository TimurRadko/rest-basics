package com.epam.esm.service;

import com.epam.esm.service.dto.OrderDto;

import java.util.List;
import java.util.Optional;

/** * This interface describes specific realization of CRUD operation on Order Entities */
public interface OrderService extends Service<OrderDto> {
  /**
   * * This method describes a general getAll (getting a list of all OrderDto) operation for all
   * OrderDtos, from persistence layer
   *
   * @param page - the parameter describes current page
   * @param size - the parameter describes quantity of the Orders for one page
   * @param id - passed into the method id Entity's parameter that required for work with the DB
   * @return List<OrderDto> - List of OrderDto contained in one of all tables in the DB
   */
  List<OrderDto> getAllOrdersByUserId(int page, int size, long id);

  /**
   * * This method describes a general get operation for all Orders, from persistence layer
   *
   * @param id - passed into the method id Entity's parameter that required for work with the DB
   * @return Optional<OrderDto> - container that is contained OrderDto
   */
  Optional<OrderDto> getById(long id);
}
