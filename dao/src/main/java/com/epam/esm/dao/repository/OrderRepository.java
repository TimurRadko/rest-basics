package com.epam.esm.dao.repository;

import com.epam.esm.dao.entity.Order;

import java.util.List;
import java.util.Optional;

/** * This interface describes a common operations with Order's Entities situated in the DB */
public interface OrderRepository {

  List<Order> getOrdersByUserId(long id);

  Optional<Order> getOrderById(long id);
}
