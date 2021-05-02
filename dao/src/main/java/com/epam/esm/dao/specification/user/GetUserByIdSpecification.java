package com.epam.esm.dao.specification.user;

import com.epam.esm.dao.entity.User;
import com.epam.esm.dao.specification.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public final class GetUserByIdSpecification implements Specification<User> {
  private final long id;

  public GetUserByIdSpecification(long id) {
    this.id = id;
  }

  @Override
  public CriteriaQuery<User> getCriteriaQuery(CriteriaBuilder builder) {
    CriteriaQuery<User> criteria = builder.createQuery(User.class);
    Root<User> userRoot = criteria.from(User.class);
    criteria.select(userRoot).distinct(true);
    criteria.where(builder.equal(userRoot.get("id"), id));
    return criteria;
  }
}
