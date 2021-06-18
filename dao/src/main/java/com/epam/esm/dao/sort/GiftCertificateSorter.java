package com.epam.esm.dao.sort;

import com.epam.esm.dao.entity.GiftCertificate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class GiftCertificateSorter {

  public void sort(CriteriaQuery<GiftCertificate> criteria, CriteriaBuilder builder, Root<GiftCertificate> root, List<String> sorts) {
    List<Order> orders = new ArrayList<>();
    nameSort(builder, root, sorts, orders);
    descriptionSort(builder, root, sorts, orders);
    createDateSort(builder, root, sorts, orders);
    lastUpdateDateSort(builder, root, sorts, orders);
    criteria.orderBy(orders);
  }

  private void lastUpdateDateSort(
      CriteriaBuilder builder, Root<GiftCertificate> root, List<String> sorts, List<Order> orders) {
    for (String sort : sorts) {
      if (sort.equals("last-update-date-asc")) {
        orders.add(builder.asc(root.get("lastUpdateDate")));
      } else if (sort.equals("last-update-date-desc")) {
        orders.add(builder.desc(root.get("lastUpdateDate")));
      }
    }
  }

  private void createDateSort(
      CriteriaBuilder builder, Root<GiftCertificate> root, List<String> sorts, List<Order> orders) {
    for (String sort : sorts) {
      if (sort.equals("create-date-asc")) {
        orders.add(builder.asc(root.get("createDate")));
      } else if (sort.equals("create-date-desc")) {
        orders.add(builder.desc(root.get("createDate")));
      }
    }
  }

  private void descriptionSort(
      CriteriaBuilder builder, Root<GiftCertificate> root, List<String> sorts, List<Order> orders) {
    for (String sort : sorts) {
      if (sort.equals("description-asc")) {
        orders.add(builder.asc(root.get("description")));
      } else if (sort.equals("description-desc")) {
        orders.add(builder.desc(root.get("description")));
      }
    }
  }

  private void nameSort(
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
