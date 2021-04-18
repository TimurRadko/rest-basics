package com.epam.esm.dao.repository.impl;

import com.epam.esm.dao.entity.Order;
import com.epam.esm.dao.repository.OrderRepository;
import com.epam.esm.dao.specification.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
  private JdbcTemplate jdbcTemplate;

  @Autowired
  public OrderRepositoryImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Optional<Order> save(Order order) {
    return Optional.empty();
  }

  @Override
  public List<Order> getEntityListBySpecification(Specification specification) {
    return jdbcTemplate.query(
        specification.getQuery(),
        specification.getArgs(),
        new BeanPropertyRowMapper<>(Order.class));
  }

  @Override
  public Optional<Order> getEntityBySpecification(Specification specification) {
    try {
      Order order =
          jdbcTemplate.queryForObject(
              specification.getQuery(),
              specification.getArgs(),
              new BeanPropertyRowMapper<>(Order.class));
      return Optional.ofNullable(order);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public int delete(long id) {
    return 0;
  }
}
