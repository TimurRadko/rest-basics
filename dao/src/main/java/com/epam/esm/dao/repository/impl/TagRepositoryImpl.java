package com.epam.esm.dao.repository.impl;

import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.repository.TagRepository;
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
public class TagRepositoryImpl implements TagRepository {
  private final EntityManager entityManager;

  @Autowired
  public TagRepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<Tag> getEntityList(Specification<Tag> specification) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Tag> criteriaQuery = specification.getCriteriaQuery(builder);
    return entityManager.createQuery(criteriaQuery).getResultList();
  }

  @Override
  public List<Tag> getEntityListWithPagination(
      Specification<Tag> specification, Integer page, Integer size) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Tag> criteriaQuery = specification.getCriteriaQuery(builder);
    return entityManager
        .createQuery(criteriaQuery)
        .setFirstResult((page - 1) * size)
        .setMaxResults(size)
        .getResultList();
  }

  @Override
  @Transactional
  public Optional<Tag> save(Tag tag) {
    entityManager.persist(tag);
    return Optional.of(tag);
  }

  @Override
  public Optional<Tag> getEntity(Specification<Tag> specification) {
    try {
      CriteriaBuilder builder = entityManager.getCriteriaBuilder();
      CriteriaQuery<Tag> criteriaQuery = specification.getCriteriaQuery(builder);
      return Optional.of(entityManager.createQuery(criteriaQuery).setMaxResults(1).getSingleResult());
    } catch (NoResultException e) {
      return Optional.empty();
    }
  }

  @Override
  @Transactional
  public int delete(long id) {
    return entityManager
        .createQuery("DELETE FROM Tag t WHERE t.id = :id")
        .setParameter("id", id)
        .executeUpdate();
  }
}
