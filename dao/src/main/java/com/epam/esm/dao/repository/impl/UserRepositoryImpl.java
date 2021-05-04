package com.epam.esm.dao.repository.impl;

import com.epam.esm.dao.entity.User;
import com.epam.esm.dao.repository.UserRepository;
import com.epam.esm.dao.specification.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
  private final EntityManager entityManager;

  @Autowired
  public UserRepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<User> getEntityList(Specification<User> specification) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<User> criteriaQuery = specification.getCriteriaQuery(builder);
    return entityManager.createQuery(criteriaQuery).getResultList();
  }

  @Override
  public List<User> getEntityListWithPagination(
      Specification<User> specification, Integer page, Integer size) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<User> criteriaQuery = specification.getCriteriaQuery(builder);
    return entityManager
        .createQuery(criteriaQuery)
        .setFirstResult((page - 1) * size)
        .setMaxResults(size)
        .getResultList();
  }

  @Override
  public Optional<User> getEntity(Specification<User> specification) {
    try {
      CriteriaBuilder builder = entityManager.getCriteriaBuilder();
      CriteriaQuery<User> criteriaQuery = specification.getCriteriaQuery(builder);
      return Optional.of(entityManager.createQuery(criteriaQuery).getSingleResult());
    } catch (NoResultException e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<User> update(User user) {
    return Optional.of(entityManager.merge(user));
  }
}
