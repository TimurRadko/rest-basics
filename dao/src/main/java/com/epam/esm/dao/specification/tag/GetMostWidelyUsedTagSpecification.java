package com.epam.esm.dao.specification.tag;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Order;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.specification.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

public class GetMostWidelyUsedTagSpecification implements Specification<Tag> {
  private final long id;

  public GetMostWidelyUsedTagSpecification(long id) {
    this.id = id;
  }

  @Override
  public CriteriaQuery<Tag> getCriteriaQuery(CriteriaBuilder builder) {
    CriteriaQuery<Tag> criteria = builder.createQuery(Tag.class);
    Root<Tag> tagRoot = criteria.from(Tag.class);
    Join<Tag, GiftCertificate> tagGiftCertificateJoin = tagRoot.join("giftCertificates");
    Join<GiftCertificate, Order> giftCertificateOrderJoin = tagRoot.join("orders");
    Path<String> userIdPath = giftCertificateOrderJoin.get("userId");
    criteria.where(builder.equal(userIdPath.get("id")));
//
//      Path<String> giftCertificateNamePath = tagsJoin.get("id");
//      Join<Tag, Order>
    return null;
  }
}
