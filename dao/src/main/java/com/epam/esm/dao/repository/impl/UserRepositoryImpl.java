package com.epam.esm.dao.repository.impl;

import com.epam.esm.dao.entity.User;
import com.epam.esm.dao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
  private EntityManager entityManager;

  @Autowired
  public UserRepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<User> getAllUsers() {
    return entityManager.createQuery("SELECT t FROM User t", User.class).getResultList();
  }

  @Override
  public Optional<User> getUserById(long id) {
    return Optional.of(entityManager.find(User.class, id));
  }
}
