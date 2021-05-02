package com.epam.esm.dao.specification.tag;

import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.specification.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public final class GetAllTagsSpecification implements Specification<Tag> {
  private final String sort;

  public GetAllTagsSpecification(String sort) {
    this.sort = sort;
  }

  @Override
  public CriteriaQuery<Tag> getCriteriaQuery(CriteriaBuilder builder) {
    CriteriaQuery<Tag> criteria = builder.createQuery(Tag.class);
    Root<Tag> tagRoot = criteria.from(Tag.class);
    if (sort == null) {
      return criteria.orderBy(builder.asc(tagRoot.get("id")));
    }
    if (sort.equals("name-asc")) {
      criteria.orderBy(builder.asc(tagRoot.get("name")));
    } else if (sort.equals("name-desc")) {
      criteria.orderBy(builder.desc(tagRoot.get("name")));
    }
    return criteria;
  }
}
