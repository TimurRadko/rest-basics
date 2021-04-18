package com.epam.esm.dao.specification.order;

import com.epam.esm.dao.specification.Specification;

public class GetAllOrdersByUserIdSpecification implements Specification {
  private final long id;

  private static final String QUERY =
      "SELECT id, user_id, cost, order_date FROM orders WHERE user_id=?;";

  public GetAllOrdersByUserIdSpecification(long id) {
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
