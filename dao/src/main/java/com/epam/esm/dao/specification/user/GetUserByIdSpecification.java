package com.epam.esm.dao.specification.user;

import com.epam.esm.dao.entity.Users;
import com.epam.esm.dao.specification.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public final class GetUserByIdSpecification implements Specification<Users> {
  private final long id;

  public GetUserByIdSpecification(long id) {
    this.id = id;
  }

  @Override
  public CriteriaQuery<Users> getCriteriaQuery(CriteriaBuilder builder) {
    CriteriaQuery<Users> criteria = builder.createQuery(Users.class);
    Root<Users> userRoot = criteria.from(Users.class);
    criteria.select(userRoot).distinct(true);
    criteria.where(builder.equal(userRoot.get("id"), id));
    return criteria;
  }
}
