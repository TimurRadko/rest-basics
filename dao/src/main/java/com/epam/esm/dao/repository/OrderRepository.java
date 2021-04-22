package com.epam.esm.dao.repository;

import com.epam.esm.dao.entity.Order;

import java.util.Optional;

/** * This interface describes a common operations with Order's Entities situated in the DB */
public interface OrderRepository extends Repository<Order> {
  /**
   * * This method describes a general save (create) operation for all Tags, located in the DB
   *
   * @param order - Order, which transmitted in the method as a args
   * @return Optional<Order> - container that is contained Tag
   */
  Optional<Order> save(Order order);
}
