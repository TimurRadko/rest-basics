package com.epam.esm.dao.repository.impl;

import com.epam.esm.dao.entity.Users;
import com.epam.esm.dao.repository.UserRepository;
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
public class UserRepositoryImpl implements UserRepository {
  private final EntityManager entityManager;

  @Autowired
  public UserRepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<Users> getEntityList(Specification<Users> specification) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Users> criteriaQuery = specification.getCriteriaQuery(builder);
    return entityManager.createQuery(criteriaQuery).getResultList();
  }

  @Override
  public List<Users> getEntityListWithPagination(
      Specification<Users> specification, Integer page, Integer size) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Users> criteriaQuery = specification.getCriteriaQuery(builder);
    return entityManager
        .createQuery(criteriaQuery)
        .setFirstResult((page - 1) * size)
        .setMaxResults(size)
        .getResultList();
  }

  @Override
  public Optional<Users> getEntity(Specification<Users> specification) {
    try {
      CriteriaBuilder builder = entityManager.getCriteriaBuilder();
      CriteriaQuery<Users> criteriaQuery = specification.getCriteriaQuery(builder);
      return Optional.of(entityManager.createQuery(criteriaQuery).getSingleResult());
    } catch (NoResultException e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<Users> update(Users users) {
    return Optional.of(entityManager.merge(users));
  }

  @Override
  @Transactional
  public Optional<Users> save(Users user) {
    entityManager.persist(user);
    return Optional.of(user);
  }
}
