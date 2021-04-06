package com.epam.esm.dao.specification;

/**
 * * This interface describes work with SQL queries and parameters which passed with the HTTP
 * request
 */
public interface Specification {
  /**
   * * This method allows getting SQL Query
   *
   * @return String - the string representation is an SQL query
   */
  String getQuery();

  /**
   * * This method allows getting all parameters required for the work with the DB
   *
   * @return Object[] - the array consist all parameters, which were passed with the HTTP request
   */
  Object[] getArgs();
}
