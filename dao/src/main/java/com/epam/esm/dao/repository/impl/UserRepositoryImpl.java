package com.epam.esm.dao.repository.impl;

import com.epam.esm.dao.entity.User;
import com.epam.esm.dao.repository.UserRepository;
import com.epam.esm.dao.specification.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
  private JdbcTemplate jdbcTemplate;

  @Autowired
  public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<User> getEntityListBySpecification(Specification specification) {
    return jdbcTemplate.query(
        specification.getQuery(), specification.getArgs(), new BeanPropertyRowMapper<>(User.class));
  }

  @Override
  public Optional<User> getEntityBySpecification(Specification specification) {
    try {
      User user =
          jdbcTemplate.queryForObject(
              specification.getQuery(),
              specification.getArgs(),
              new BeanPropertyRowMapper<>(User.class));
      return Optional.ofNullable(user);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<User> save(User user) {
    return Optional.empty();
  }

  @Override
  public int delete(long id) {
    return 0;
  }
}
