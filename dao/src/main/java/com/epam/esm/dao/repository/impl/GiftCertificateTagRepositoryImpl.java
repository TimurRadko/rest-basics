package com.epam.esm.dao.repository.impl;

import com.epam.esm.dao.entity.GiftCertificateTag;
import com.epam.esm.dao.repository.GiftCertificateTagRepository;
import com.epam.esm.dao.specification.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateTagRepositoryImpl implements GiftCertificateTagRepository {
  private EntityManager entityManager;

  @Autowired
  public GiftCertificateTagRepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Optional<GiftCertificateTag> save(GiftCertificateTag giftCertificateTag) {
    return Optional.of(entityManager.merge(giftCertificateTag));
  }

  @Override
  public List<GiftCertificateTag> getEntityListBySpecification(
      Specification<GiftCertificateTag> specification) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<GiftCertificateTag> criteriaQuery = specification.getCriteriaQuery(builder);
    return entityManager.createQuery(criteriaQuery).getResultList();
  }

  @Override
  public Optional<GiftCertificateTag> getEntityBySpecification(
      Specification<GiftCertificateTag> specification) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<GiftCertificateTag> criteriaQuery = specification.getCriteriaQuery(builder);
    return Optional.of(entityManager.createQuery(criteriaQuery).getSingleResult());
  }

  @Override
  public int delete(long id) {
    return entityManager
        .createQuery("DELETE FROM GiftCertificateTag t WHERE t.id = :tagId")
        .setParameter("tagId", id)
        .executeUpdate();
  }
}
