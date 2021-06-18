package com.epam.esm.dao.specification.user;

import com.epam.esm.dao.entity.Users;
import com.epam.esm.dao.specification.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

public final class GetAllUsersSpecification implements Specification<Users> {

  @Override
  public CriteriaQuery<Users> getCriteriaQuery(CriteriaBuilder builder) {
    CriteriaQuery<Users> criteria = builder.createQuery(Users.class);
    Root<Users> userRoot = criteria.from(Users.class);
    userRoot.fetch("orders", JoinType.LEFT);
    criteria.select(userRoot).distinct(true);
    return criteria.orderBy(builder.asc(userRoot.get("id")));
  }
}
