package com.epam.esm.dao.specification.gift;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.specification.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

public final class GetAllGiftCertificatesSpecification implements Specification<GiftCertificate> {
  private final String sort;

  private static final String QUERY =
      "SELECT id, name, description, price, "
          + "duration, create_date, last_update_date FROM gift_certificates "
          + "ORDER BY "
          + "CASE WHEN ? ='name-asc' THEN name END ASC, "
          + "CASE WHEN ? ='name-desc' THEN name END DESC, "
          + "CASE WHEN ? ='create-date-asc' THEN create_date END ASC, "
          + "CASE WHEN ? ='create-date-desc' THEN create_date END DESC, id ASC";

  public GetAllGiftCertificatesSpecification(String sort) {
    this.sort = sort;
  }

  @Override
  public CriteriaQuery<GiftCertificate> getCriteriaQuery(CriteriaBuilder builder) {
    CriteriaQuery<GiftCertificate> criteria = builder.createQuery(GiftCertificate.class);
    Root<GiftCertificate> root = criteria.from(GiftCertificate.class);
    root.fetch("tags", JoinType.LEFT);
    criteria.select(root).distinct(true);
//    if (sort == null) {
//      return criteria.orderBy(builder.asc(root.get("id")));
//    }
//    if (sort.equals("name-asc")) {
//      criteria.orderBy(builder.asc(root.get("name")));
//    } else if (sort.equals("name-desc")) {
//      criteria.orderBy(builder.desc(root.get("name")));
//    }
//
//    if (sort.equals("create-date-asc")) {
//      criteria.orderBy(builder.asc(root.get("create-date")));
//    } else if (sort.equals("create-date-desc")) {
//      criteria.orderBy(builder.desc(root.get("create-date")));
//    }

    return criteria;
  }
}
