package com.epam.esm.dao.specification.order;

import com.epam.esm.dao.entity.Orders;
import com.epam.esm.dao.specification.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public final class GetOrderByIdSpecification implements Specification<Orders> {
  private final long id;

  public GetOrderByIdSpecification(long id) {
    this.id = id;
  }

  @Override
  public CriteriaQuery<Orders> getCriteriaQuery(CriteriaBuilder builder) {
    CriteriaQuery<Orders> criteria = builder.createQuery(Orders.class);
    Root<Orders> orderRoot = criteria.from(Orders.class);
    criteria.select(orderRoot);
    criteria.where(builder.equal(orderRoot.get("id"), id));
    return criteria;
  }
}
