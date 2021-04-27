package com.epam.esm.dao.repository.impl;

import com.epam.esm.dao.entity.Order;
import com.epam.esm.dao.repository.OrderRepository;
import com.epam.esm.dao.specification.Specification;
import com.epam.esm.dao.validator.RepositoryPageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
  private final EntityManager entityManager;
  private final RepositoryPageValidator repositoryPageValidator;

  @Autowired
  public OrderRepositoryImpl(
      EntityManager entityManager, RepositoryPageValidator repositoryPageValidator) {
    this.entityManager = entityManager;
    this.repositoryPageValidator = repositoryPageValidator;
  }

  @Override
  public List<Order> getEntityListBySpecification(Specification<Order> specification) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Order> criteriaQuery = specification.getCriteriaQuery(builder);
    return entityManager.createQuery(criteriaQuery).getResultList();
  }

  @Override
  public List<Order> getEntityListWithPaginationBySpecification(
      Specification<Order> specification, Integer page, Integer size) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    repositoryPageValidator.isValid(page, size, builder, entityManager);
    CriteriaQuery<Order> criteriaQuery = specification.getCriteriaQuery(builder);
    return entityManager.createQuery(criteriaQuery).getResultList();
  }

  @Override
  public Optional<Order> getEntityBySpecification(Specification<Order> specification) {
    try {
      CriteriaBuilder builder = entityManager.getCriteriaBuilder();
      CriteriaQuery<Order> criteriaQuery = specification.getCriteriaQuery(builder);
      return Optional.of(entityManager.createQuery(criteriaQuery).getSingleResult());
    } catch (NoResultException e) {
      return Optional.empty();
    }
  }

  @Override
  @Transactional
  public Optional<Order> save(Order order) {
    LocalDateTime now = LocalDateTime.now();
    order.setOrderDate(now);
    entityManager.persist(order);
    return Optional.of(order);
  }
}
