package com.epam.esm.dao.repository.impl;

import com.epam.esm.dao.entity.Order;
import com.epam.esm.dao.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
  private EntityManager entityManager;

  @Autowired
  public OrderRepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<Order> getOrdersByUserId(long id) {
    return entityManager
        .createQuery("SELECT t FROM Order t WHERE user_id=?1", Order.class)
        .setParameter(1, id)
        .getResultList();
  }

  @Override
  public Optional<Order> getOrderById(long id) {
    return Optional.of(entityManager.find(Order.class, id));
  }
}
