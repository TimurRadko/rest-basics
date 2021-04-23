package com.epam.esm.dao.specification.order;

import com.epam.esm.dao.entity.Order;
import com.epam.esm.dao.entity.User;
import com.epam.esm.dao.specification.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

public class GetAllOrdersByUserIdSpecification implements Specification<Order> {
  private final long userId;

  public GetAllOrdersByUserIdSpecification(long userId) {
    this.userId = userId;
  }

  @Override
  public CriteriaQuery<Order> getCriteriaQuery(CriteriaBuilder builder) {
    CriteriaQuery<Order> criteria = builder.createQuery(Order.class);
    Root<Order> tagRoot = criteria.from(Order.class);
    Join<Order, User> orderUserJoin = tagRoot.join("user");
    Path<String> userIdPath = orderUserJoin.get("id");
    criteria.select(tagRoot).distinct(true);
    criteria.where(builder.equal(userIdPath, userId));
    return criteria;
  }
}
