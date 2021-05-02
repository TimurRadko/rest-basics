package com.epam.esm.dao.specification.user;

import com.epam.esm.dao.entity.User;
import com.epam.esm.dao.specification.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

public final class GetAllUsersSpecification implements Specification<User> {

  @Override
  public CriteriaQuery<User> getCriteriaQuery(CriteriaBuilder builder) {
    CriteriaQuery<User> criteria = builder.createQuery(User.class);
    Root<User> userRoot = criteria.from(User.class);
    userRoot.fetch("orders", JoinType.LEFT);
    criteria.select(userRoot).distinct(true);
    return criteria.orderBy(builder.asc(userRoot.get("id")));
  }
}
