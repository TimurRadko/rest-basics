package com.epam.esm.dao.repository.impl;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.repository.GiftCertificateRepository;
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
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
  private final EntityManager entityManager;
  private final RepositoryPageValidator repositoryPageValidator;

  @Autowired
  public GiftCertificateRepositoryImpl(
      EntityManager entityManager, RepositoryPageValidator repositoryPageValidator) {
    this.entityManager = entityManager;
    this.repositoryPageValidator = repositoryPageValidator;
  }

  @Override
  @Transactional
  public Optional<GiftCertificate> update(GiftCertificate giftCertificate) {
    giftCertificate.setLastUpdateDate(LocalDateTime.now());
    return Optional.of(entityManager.merge(giftCertificate));
  }

  @Override
  @Transactional
  public Optional<GiftCertificate> save(GiftCertificate giftCertificate) {
    LocalDateTime now = LocalDateTime.now();
    giftCertificate.setCreateDate(now);
    giftCertificate.setLastUpdateDate(now);
    entityManager.persist(giftCertificate);
    return Optional.of(giftCertificate);
  }

  @Override
  public List<GiftCertificate> getEntityListBySpecification(
      Specification<GiftCertificate> specification) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<GiftCertificate> criteriaQuery = specification.getCriteriaQuery(builder);
    return entityManager.createQuery(criteriaQuery).getResultList();
  }

  @Override
  public List<GiftCertificate> getEntityListWithPaginationBySpecification(
      Specification<GiftCertificate> specification, Integer page, Integer size) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    repositoryPageValidator.isValid(page, size, builder, entityManager);
    CriteriaQuery<GiftCertificate> criteriaQuery = specification.getCriteriaQuery(builder);
    return entityManager
        .createQuery(criteriaQuery)
        .setFirstResult((page - 1) * size)
        .setMaxResults(size)
        .getResultList();
  }

  @Override
  public Optional<GiftCertificate> getEntityBySpecification(
      Specification<GiftCertificate> specification) {
    try {
      CriteriaBuilder builder = entityManager.getCriteriaBuilder();
      CriteriaQuery<GiftCertificate> criteriaQuery = specification.getCriteriaQuery(builder);
      return Optional.of(entityManager.createQuery(criteriaQuery).getSingleResult());
    } catch (NoResultException e) {
      return Optional.empty();
    }
  }

  @Override
  @Transactional
  public int delete(long id) {
    return entityManager
        .createQuery("DELETE FROM GiftCertificate t WHERE t.id = :id")
        .setParameter("id", id)
        .executeUpdate();
  }
}
