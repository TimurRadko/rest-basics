package com.epam.esm.dao.repository.impl;

import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.repository.TagRepository;
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
public class TagRepositoryImpl implements TagRepository {
  private final EntityManager entityManager;
  private final RepositoryPageValidator repositoryPageValidator;

  @Autowired
  public TagRepositoryImpl(
      EntityManager entityManager, RepositoryPageValidator repositoryPageValidator) {
    this.entityManager = entityManager;
    this.repositoryPageValidator = repositoryPageValidator;
  }

  @Override
  public List<Tag> getEntityListBySpecification(Specification<Tag> specification) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Tag> criteriaQuery = specification.getCriteriaQuery(builder);
    return entityManager.createQuery(criteriaQuery).getResultList();
  }

  @Override
  public List<Tag> getEntityListWithPaginationBySpecification(
      Specification<Tag> specification, Integer page, Integer size) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    repositoryPageValidator.isValid(page, size, builder, entityManager);
    CriteriaQuery<Tag> criteriaQuery = specification.getCriteriaQuery(builder);
    return entityManager.createQuery(criteriaQuery).getResultList();
  }

  @Override
  @Transactional
  public Optional<Tag> save(Tag tag) {
    entityManager.persist(tag);
    return Optional.of(tag);
  }

  @Override
  public Optional<Tag> getEntityBySpecification(Specification<Tag> specification) {
    try {
      CriteriaBuilder builder = entityManager.getCriteriaBuilder();
      CriteriaQuery<Tag> criteriaQuery = specification.getCriteriaQuery(builder);
      return Optional.of(entityManager.createQuery(criteriaQuery).getSingleResult());
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
