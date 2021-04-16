package com.epam.esm.dao.specification.user;

import com.epam.esm.dao.specification.Specification;

public class GetUserByIdSpecification implements Specification {
  private final long id;

  private static final String QUERY = "SELECT id, login, password, account FROM users WHERE id=?;";

  public GetUserByIdSpecification(long id) {
    this.id = id;
  }

  @Override
  public String getQuery() {
    return QUERY;
  }

  @Override
  public Object[] getArgs() {
    return new Object[] {id};
  }
}
