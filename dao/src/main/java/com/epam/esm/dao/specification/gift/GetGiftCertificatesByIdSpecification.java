package com.epam.esm.dao.specification.gift;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.specification.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

public final class GetGiftCertificatesByIdSpecification implements Specification<GiftCertificate> {
  private final long id;

  public GetGiftCertificatesByIdSpecification(long id) {
    this.id = id;
  }

  @Override
  public CriteriaQuery<GiftCertificate> getCriteriaQuery(CriteriaBuilder builder) {
    CriteriaQuery<GiftCertificate> criteria = builder.createQuery(GiftCertificate.class);
    Root<GiftCertificate> giftCertificateRoot = criteria.from(GiftCertificate.class);
    giftCertificateRoot.fetch("tags", JoinType.LEFT);
    criteria.select(giftCertificateRoot).distinct(true);
    criteria.where(builder.equal(giftCertificateRoot.get("id"), id));
    return criteria;
  }
}
