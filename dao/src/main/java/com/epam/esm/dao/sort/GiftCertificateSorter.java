package com.epam.esm.dao.sort;

import com.epam.esm.dao.entity.GiftCertificate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class GiftCertificateSorter {

  public void sort(
      CriteriaQuery<GiftCertificate> criteria,
      CriteriaBuilder builder,
      Root<GiftCertificate> root,
      String sort) {
    if (sort.equals("name-asc")) {
      criteria.orderBy(builder.asc(root.get("name")));
    } else if (sort.equals("name-desc")) {
      criteria.orderBy(builder.desc(root.get("name")));
    }

    if (sort.equals("description-asc")) {
      criteria.orderBy(builder.asc(root.get("description")));
    } else if (sort.equals("description-desc")) {
      criteria.orderBy(builder.desc(root.get("description")));
    }

//    if (sort.equals("create-date-asc")) {
//      criteria.orderBy(builder.asc(root.get("create_date")));
//    } else if (sort.equals("create-date-desc")) {
//      criteria.orderBy(builder.desc(root.get("create_date")));
//    }
  }
}
