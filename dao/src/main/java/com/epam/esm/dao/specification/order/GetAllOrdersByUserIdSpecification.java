package com.epam.esm.dao.specification.order;

import com.epam.esm.dao.entity.Orders;
import com.epam.esm.dao.entity.User;
import com.epam.esm.dao.specification.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

public final class GetAllOrdersByUserIdSpecification implements Specification<Orders> {
  private final long userId;

  public GetAllOrdersByUserIdSpecification(long userId) {
    this.userId = userId;
  }

  @Override
  public CriteriaQuery<Orders> getCriteriaQuery(CriteriaBuilder builder) {
    CriteriaQuery<Orders> criteria = builder.createQuery(Orders.class);
    Root<Orders> ordersRoot = criteria.from(Orders.class);
    Join<Orders, User> orderUserJoin = ordersRoot.join("user");
    Path<String> userIdPath = orderUserJoin.get("id");
    criteria.select(ordersRoot).distinct(true);
    criteria.where(builder.equal(userIdPath, userId));
    return criteria.orderBy(builder.asc(ordersRoot.get("id")));
  }
}
