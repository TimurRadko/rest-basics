package com.epam.esm.dao.repository.impl;

import com.epam.esm.dao.entity.OrdersGiftCertificate;
import com.epam.esm.dao.repository.OrdersGiftCertificateRepository;
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
public class OrdersGiftCertificateRepositoryImpl implements OrdersGiftCertificateRepository {
  private EntityManager entityManager;

  @Autowired
  public OrdersGiftCertificateRepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<OrdersGiftCertificate> getEntityListBySpecification(
      Specification<OrdersGiftCertificate> specification) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<OrdersGiftCertificate> criteriaQuery = specification.getCriteriaQuery(builder);
    return entityManager.createQuery(criteriaQuery).getResultList();
  }

  @Override
  public List<OrdersGiftCertificate> getEntityListWithPaginationBySpecification(
      Specification<OrdersGiftCertificate> specification, Integer page, Integer size) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<OrdersGiftCertificate> criteriaQuery = specification.getCriteriaQuery(builder);
    return entityManager.createQuery(criteriaQuery).getResultList();
  }

  @Override
  public Optional<OrdersGiftCertificate> getEntityBySpecification(
      Specification<OrdersGiftCertificate> specification) {
    try {
      CriteriaBuilder builder = entityManager.getCriteriaBuilder();
      CriteriaQuery<OrdersGiftCertificate> criteriaQuery = specification.getCriteriaQuery(builder);
      return Optional.of(entityManager.createQuery(criteriaQuery).getSingleResult());
    } catch (NoResultException e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<OrdersGiftCertificate> save(OrdersGiftCertificate ordersGiftCertificate) {
    entityManager.persist(ordersGiftCertificate);
    return Optional.of(ordersGiftCertificate);
  }
}
