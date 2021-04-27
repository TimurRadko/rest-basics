package com.epam.esm.dao.repository.impl;

import com.epam.esm.dao.entity.User;
import com.epam.esm.dao.repository.UserRepository;
import com.epam.esm.dao.specification.Specification;
import com.epam.esm.dao.validator.RepositoryPageValidator;
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
  private final EntityManager entityManager;
  private final RepositoryPageValidator repositoryPageValidator;

  @Autowired
  public UserRepositoryImpl(
      EntityManager entityManager, RepositoryPageValidator repositoryPageValidator) {
    this.entityManager = entityManager;
    this.repositoryPageValidator = repositoryPageValidator;
  }

  @Override
  public List<User> getEntityListBySpecification(Specification<User> specification) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<User> criteriaQuery = specification.getCriteriaQuery(builder);
    return entityManager.createQuery(criteriaQuery).getResultList();
  }

  @Override
  public List<User> getEntityListWithPaginationBySpecification(
      Specification<User> specification, Integer page, Integer size) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    repositoryPageValidator.isValid(page, size, builder, entityManager);
    CriteriaQuery<User> criteriaQuery = specification.getCriteriaQuery(builder);
    return entityManager.createQuery(criteriaQuery).getResultList();
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
