package com.epam.esm.dao.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

/**
 * * This interface describes work with SQL queries and parameters which passed with the HTTP
 * request
 */
public interface Specification<T> {
  /**
   * * This method describes getting CriteriaQuery<T> for further manipulation with data in the
   * database
   *
   * @param builder - To work with CriteriaQuery<T>, the arguments pass CriteriaBuilder
   * @return CriteriaQuery<T> - CriteriaQuery<T> typed one of the Entity in the database
   */
  CriteriaQuery<T> getCriteriaQuery(CriteriaBuilder builder);
}
