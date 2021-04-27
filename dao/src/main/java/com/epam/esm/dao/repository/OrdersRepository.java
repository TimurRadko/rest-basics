package com.epam.esm.dao.repository;

import com.epam.esm.dao.entity.Orders;

import java.util.Optional;

/** * This interface describes a common operations with Order's Entities situated in the DB */
public interface OrdersRepository extends Repository<Orders> {
  /**
   * * This method describes a general save (create) operation for all Orders, located in the DB
   *
   * @param orders - Orders, which transmitted in the method as a args
   * @return Optional<Orders> - container that is contained Orders
   */
  Optional<Orders> save(Orders orders);
}
