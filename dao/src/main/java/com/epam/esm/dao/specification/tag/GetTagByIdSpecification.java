package com.epam.esm.dao.specification.tag;

import com.epam.esm.dao.specification.Specification;

public class GetTagByIdSpecification implements Specification {
  private final long id;

  private static final String QUERY = "SELECT id, name FROM tags WHERE id=?;";

  public GetTagByIdSpecification(long id) {
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
