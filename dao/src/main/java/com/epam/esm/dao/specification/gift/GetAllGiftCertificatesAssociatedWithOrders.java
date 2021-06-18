package com.epam.esm.dao.specification.gift;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Orders;
import com.epam.esm.dao.specification.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

public class GetAllGiftCertificatesAssociatedWithOrders implements Specification<GiftCertificate> {
  private final long id;

  public GetAllGiftCertificatesAssociatedWithOrders(long id) {
    this.id = id;
  }

  @Override
  public CriteriaQuery<GiftCertificate> getCriteriaQuery(CriteriaBuilder builder) {
    CriteriaQuery<GiftCertificate> criteria = builder.createQuery(GiftCertificate.class);
    Root<GiftCertificate> giftCertificateRoot = criteria.from(GiftCertificate.class);
    Join<GiftCertificate, Orders> giftCertificateOrdersJoin = giftCertificateRoot.join("orders");
    Path<String> ordersIdPath = giftCertificateOrdersJoin.get("id");
    criteria.select(giftCertificateRoot).distinct(true);
    criteria.where(
        builder.and(builder.equal(giftCertificateRoot.get("id"), id), ordersIdPath.isNotNull()));
    return criteria;
  }
}
