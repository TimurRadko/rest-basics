package com.epam.esm.dao.repository.impl;

import com.epam.esm.dao.entity.User;
import com.epam.esm.dao.repository.UserRepository;
import com.epam.esm.dao.specification.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
  public List<User> getEntityListBySpecification(Specification<User> specification) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<User> criteriaQuery = specification.getCriteriaQuery(builder);
    return entityManager.createQuery(criteriaQuery).getResultList();
  }

  @Override
  public List<User> getEntityListWithPaginationBySpecification(
      Specification<User> specification, int page, int size) {
    return null;
  }

  @Override
  public Optional<User> getEntityBySpecification(Specification<User> specification) {
    try {
      CriteriaBuilder builder = entityManager.getCriteriaBuilder();
      CriteriaQuery<User> criteriaQuery = specification.getCriteriaQuery(builder);
      return Optional.of(entityManager.createQuery(criteriaQuery).getSingleResult());
    } catch (NoResultException e) {
      return Optional.empty();
    }
  }

  @Override
  @Transactional
  public Optional<User> update(User user) {
    return Optional.of(entityManager.merge(user));
  }
}
