package com.epam.esm.dao.repository.impl;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.repository.GiftCertificateRepository;
import com.epam.esm.dao.specification.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
  private EntityManager entityManager;

  @Autowired
  public GiftCertificateRepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<GiftCertificate> getGiftCertificatesBySpecification(Specification specification) {
    return entityManager
        .createQuery("SELECT t FROM GiftCertificate t", GiftCertificate.class)
        .getResultList();
  }

  @Override
  public List<GiftCertificate> getSortedGiftCertificates(String sort) {
    return entityManager
            .createQuery("SELECT t FROM GiftCertificate t ORDER BY t.name ASC", GiftCertificate.class)
            .getResultList();
  }

  @Override
  public Optional<GiftCertificate> getGiftCertificateById(long id) {
    return Optional.of(entityManager.find(GiftCertificate.class, id));
  }

  @Override
  public Optional<GiftCertificate> save(GiftCertificate giftCertificate) {
    LocalDateTime now = LocalDateTime.now();
    giftCertificate.setCreateDate(now);
    giftCertificate.setLastUpdateDate(now);
    return Optional.of(entityManager.merge(giftCertificate));
  }

  @Override
  public Optional<GiftCertificate> update(GiftCertificate giftCertificate) {
    giftCertificate.setLastUpdateDate(LocalDateTime.now());
    return Optional.of(entityManager.merge(giftCertificate));
  }

  @Override
  public int delete(long id) {
    entityManager
        .createQuery("DELETE FROM GiftCertificateTag WHERE giftCertificateId = :id")
        .setParameter("id", id)
        .executeUpdate();
    return entityManager
        .createQuery("DELETE FROM GiftCertificate WHERE id = :id")
        .setParameter("id", id)
        .executeUpdate();
  }
}
