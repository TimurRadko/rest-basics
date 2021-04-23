package com.epam.esm.dao.sort;

import com.epam.esm.dao.entity.GiftCertificate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class GiftCertificateSorter {

  public void sort(
      CriteriaQuery<GiftCertificate> criteria,
      CriteriaBuilder builder,
      Root<GiftCertificate> root,
      List<String> sorts) {
    List<Order> orders = new ArrayList<>();
    nameSorting(builder, root, sorts, orders);
    descriptionSorting(builder, root, sorts, orders);
    createDateSorting(builder, root, sorts, orders);
    lastUpdateDateSorting(builder, root, sorts, orders);
    criteria.orderBy(orders);
  }

  private void lastUpdateDateSorting(
      CriteriaBuilder builder, Root<GiftCertificate> root, List<String> sorts, List<Order> orders) {
    for (String sort : sorts) {
      if (sort.equals("last-update-date-asc")) {
        orders.add(builder.asc(root.get("lastUpdateDate")));
      } else if (sort.equals("last-update-date-desc")) {
        orders.add(builder.desc(root.get("lastUpdateDate")));
      }
    }
  }

  private void createDateSorting(
      CriteriaBuilder builder, Root<GiftCertificate> root, List<String> sorts, List<Order> orders) {
    for (String sort : sorts) {
      if (sort.equals("create-date-asc")) {
        orders.add(builder.asc(root.get("createDate")));
      } else if (sort.equals("create-date-desc")) {
        orders.add(builder.desc(root.get("createDate")));
      }
    }
  }

  private void descriptionSorting(
      CriteriaBuilder builder, Root<GiftCertificate> root, List<String> sorts, List<Order> orders) {
    for (String sort : sorts) {
      if (sort.equals("description-asc")) {
        orders.add(builder.asc(root.get("description")));
      } else if (sort.equals("description-desc")) {
        orders.add(builder.desc(root.get("description")));
      }
    }
  }

  private void nameSorting(
      CriteriaBuilder builder, Root<GiftCertificate> root, List<String> sorts, List<Order> orders) {
    for (String sort : sorts) {
      if (sort.equals("name-asc")) {
        orders.add(builder.asc(root.get("name")));
      } else if (sort.equals("name-desc")) {
        orders.add(builder.desc(root.get("name")));
      }
    }
  }
}
