package com.epam.esm.dao.specification.order;

import com.epam.esm.dao.entity.Order;
import com.epam.esm.dao.specification.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class GetOrderByIdSpecification implements Specification<Order> {
  private final long id;

  public GetOrderByIdSpecification(long id) {
    this.id = id;
  }

  @Override
  public CriteriaQuery<Order> getCriteriaQuery(CriteriaBuilder builder) {
    CriteriaQuery<Order> criteria = builder.createQuery(Order.class);
    Root<Order> orderRoot = criteria.from(Order.class);
    criteria.select(orderRoot);
    criteria.where(builder.equal(orderRoot.get("id"), id));
    return criteria;
  }
}
