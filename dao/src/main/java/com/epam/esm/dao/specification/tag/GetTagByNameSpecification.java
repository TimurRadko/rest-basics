package com.epam.esm.dao.specification.tag;

import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.specification.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public final class GetTagByNameSpecification implements Specification<Tag> {
  private final String name;

  public GetTagByNameSpecification(String name) {
    this.name = name;
  }

  @Override
  public CriteriaQuery<Tag> getCriteriaQuery(CriteriaBuilder builder) {
    CriteriaQuery<Tag> criteria = builder.createQuery(Tag.class);
    Root<Tag> root = criteria.from(Tag.class);
    criteria.select(root);
    criteria.where(builder.equal(root.get("name"), name));
    return criteria;
  }
}
