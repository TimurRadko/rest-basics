package com.epam.esm.dao.validator;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.exception.PageNotValidException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

@Component
public class RepositoryPageValidator {

  public void isValid(Integer page, Integer size, CriteriaBuilder builder, EntityManager entityManager) {
    if (page == null || size == null) {
      throw new PageNotValidException("The parameters page and size mustn't be null");
    }
    if (page <= 0) {
      throw new PageNotValidException("The page parameter must be more than 0");
    }
    if (size < 0) {
      throw new PageNotValidException("The size parameter must be positive");
    }
    CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
    countQuery.select(builder.count(countQuery.from(GiftCertificate.class)));
    Long count = entityManager.createQuery(countQuery).getSingleResult();
    if (page > count) {
      throw new PageNotValidException("The page more than Entities in the DB");
    }
  }
}
