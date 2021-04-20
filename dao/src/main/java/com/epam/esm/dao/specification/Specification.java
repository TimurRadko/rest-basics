package com.epam.esm.dao.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

/**
 * * This interface describes work with SQL queries and parameters which passed with the HTTP
 * request
 */
public interface Specification<T> {

  CriteriaQuery<T> getCriteriaQuery(CriteriaBuilder builder);
}
