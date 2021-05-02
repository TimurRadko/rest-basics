package com.epam.esm.dao.repository.impl;

import com.epam.esm.dao.entity.Orders;
import com.epam.esm.dao.repository.OrdersRepository;
import com.epam.esm.dao.specification.Specification;
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
public class OrdersRepositoryImpl implements OrdersRepository {
  private final EntityManager entityManager;

  @Autowired
  public OrdersRepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<Orders> getEntityListBySpecification(Specification<Orders> specification) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Orders> criteriaQuery = specification.getCriteriaQuery(builder);
    return entityManager.createQuery(criteriaQuery).getResultList();
  }

  @Override
  public List<Orders> getEntityListWithPaginationBySpecification(
      Specification<Orders> specification, Integer page, Integer size) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Orders> criteriaQuery = specification.getCriteriaQuery(builder);
    return entityManager
            .createQuery(criteriaQuery)
            .setFirstResult((page - 1) * size)
            .setMaxResults(size)
            .getResultList();
  }

  @Override
  public Optional<Orders> getEntityBySpecification(Specification<Orders> specification) {
    try {
      CriteriaBuilder builder = entityManager.getCriteriaBuilder();
      CriteriaQuery<Orders> criteriaQuery = specification.getCriteriaQuery(builder);
      return Optional.of(entityManager.createQuery(criteriaQuery).getSingleResult());
    } catch (NoResultException e) {
      return Optional.empty();
    }
  }

  @Override
  @Transactional
  public Optional<Orders> save(Orders orders) {
    LocalDateTime now = LocalDateTime.now();
    orders.setOrderDate(now);
    entityManager.persist(orders);
    return Optional.of(orders);
  }
}
