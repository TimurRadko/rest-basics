package com.epam.esm.dao.repository.impl;

import com.epam.esm.dao.entity.GiftCertificateTag;
import com.epam.esm.dao.repository.GiftCertificateTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
  public List<GiftCertificateTag> getGiftCertificateTags() {
    return entityManager
        .createQuery("SELECT t FROM GiftCertificateTag t", GiftCertificateTag.class)
        .getResultList();
  }

  @Override
  public List<GiftCertificateTag> getGiftCertificateTagsByGiftCertificateId(long id) {
    return entityManager
        .createQuery(
            "SELECT t FROM GiftCertificateTag t WHERE giftCertificateId=?1",
            GiftCertificateTag.class)
        .setParameter(1, id)
        .getResultList();
  }

  @Override
  public Optional<GiftCertificateTag> getGiftCertificateTagById(long id) {
    return Optional.of(entityManager.find(GiftCertificateTag.class, id));
  }

  @Override
  public Optional<GiftCertificateTag> getGiftCertificateTagByGiftCertificateIdAndTagId(
      long giftCertificateId, long tagId) {
    return Optional.of(
        entityManager
            .createQuery(
                "SELECT t FROM GiftCertificateTag t WHERE giftCertificateId=?1 AND tagId=?2",
                GiftCertificateTag.class)
            .setParameter(1, giftCertificateId)
            .setParameter(2, tagId)
            .getSingleResult());
  }

  @Override
  public Optional<GiftCertificateTag> save(GiftCertificateTag giftCertificateTag) {
    return Optional.of(entityManager.merge(giftCertificateTag));
  }

  @Override
  public int delete(long id) {
    return entityManager
        .createQuery("DELETE FROM GiftCertificateTag WHERE id = :tagId")
        .setParameter("tagId", id)
        .executeUpdate();
  }
}
