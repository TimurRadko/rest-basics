package com.epam.esm.dao.specification.tag;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Orders;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.specification.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public final class GetMostWidelyUsedTagSpecification implements Specification<Tag> {
  private final long id;

  public GetMostWidelyUsedTagSpecification(long id) {
    this.id = id;
  }

  @Override
  public CriteriaQuery<Tag> getCriteriaQuery(CriteriaBuilder builder) {
    CriteriaQuery<Tag> criteria = builder.createQuery(Tag.class);
    Root<Tag> tagRoot = criteria.from(Tag.class);
    Join<Tag, GiftCertificate> tagGiftCertificateJoin = tagRoot.join("giftCertificates");
    Join<Tag, Orders> giftCertificateOrderJoin = tagGiftCertificateJoin.join("orders");
    criteria.select(tagRoot);
    List<Order> orders = new ArrayList<>();
    criteria.where(builder.equal(giftCertificateOrderJoin.get("user"), id));
    criteria.groupBy(tagRoot.get("id"));
    orders.add(builder.desc(builder.sum(giftCertificateOrderJoin.get("cost"))));
    criteria.orderBy(orders);
    return criteria;
  }
}
