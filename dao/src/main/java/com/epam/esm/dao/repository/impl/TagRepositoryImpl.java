package com.epam.esm.dao.repository.impl;

import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.repository.TagRepository;
import com.epam.esm.dao.specification.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class TagRepositoryImpl implements TagRepository {
  private EntityManager entityManager;

  @Autowired
  public TagRepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<Tag> getTagsBySpecification(Specification specification) {
    return entityManager.createQuery("SELECT t FROM Tag t", Tag.class).getResultList();
  }

  @Override
  public List<Tag> getTagsByGiftCertificateId(long id) {
    return entityManager
        .createQuery(
            "SELECT t FROM Tag t INNER JOIN GiftCertificateTag gct ON t.id=gct.tagId WHERE gct.giftCertificateId=?1",
            Tag.class)
        .setParameter(1, id)
        .getResultList();
  }

  @Override
  public Optional<Tag> getTagById(long id) {
    return Optional.of(entityManager.find(Tag.class, id));
  }

  @Override
  public Optional<Tag> getTagByName(String name) {
    return Optional.of(
        entityManager
            .createQuery("SELECT t FROM Tag t WHERE name=?1", Tag.class)
            .setParameter(1, name)
            .getSingleResult());
  }

  @Override
  public Optional<Tag> save(Tag tag) {
    return Optional.of(entityManager.merge(tag));
  }

  @Override
  public int delete(long id) {
    return entityManager
        .createQuery("DELETE FROM Tag WHERE id = :tagId")
        .setParameter("tagId", id)
        .executeUpdate();
  }
}
