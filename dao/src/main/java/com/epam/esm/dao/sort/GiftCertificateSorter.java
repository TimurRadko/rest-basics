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
    nameSorting(criteria, builder, root, sort);
    descriptionSorting(criteria, builder, root, sort);
    createDateSorting(criteria, builder, root, sort);
    lastUpdateDateSorting(criteria, builder, root, sort);
  }

  private void lastUpdateDateSorting(
      CriteriaQuery<GiftCertificate> criteria,
      CriteriaBuilder builder,
      Root<GiftCertificate> root,
      String sort) {
    if (sort.equals("last-update-date-asc")) {
      criteria.orderBy(builder.asc(root.get("lastUpdateDate")));
    } else if (sort.equals("last-update-date-desc")) {
      criteria.orderBy(builder.desc(root.get("lastUpdateDate")));
    }
  }

  private void createDateSorting(
      CriteriaQuery<GiftCertificate> criteria,
      CriteriaBuilder builder,
      Root<GiftCertificate> root,
      String sort) {
    if (sort.equals("create-date-asc")) {
      criteria.orderBy(builder.asc(root.get("createDate")));
    } else if (sort.equals("create-date-desc")) {
      criteria.orderBy(builder.desc(root.get("createDate")));
    }
  }

  private void descriptionSorting(
      CriteriaQuery<GiftCertificate> criteria,
      CriteriaBuilder builder,
      Root<GiftCertificate> root,
      String sort) {
    if (sort.equals("description-asc")) {
      criteria.orderBy(builder.asc(root.get("description")));
    } else if (sort.equals("description-desc")) {
      criteria.orderBy(builder.desc(root.get("description")));
    }
  }

  private void nameSorting(
      CriteriaQuery<GiftCertificate> criteria,
      CriteriaBuilder builder,
      Root<GiftCertificate> root,
      String sort) {
    if (sort.equals("name-asc")) {
      criteria.orderBy(builder.asc(root.get("name")));
    } else if (sort.equals("name-desc")) {
      criteria.orderBy(builder.desc(root.get("name")));
    }
  }
}
