package com.epam.esm.dao.specification.user;

import com.epam.esm.dao.specification.Specification;

public class GetAllUsersSpecification implements Specification {
  private static final String QUERY = "SELECT id, login, password, account FROM users;";

  @Override
  public String getQuery() {
    return QUERY;
  }

  @Override
  public Object[] getArgs() {
    return new Object[] {};
  }
}
