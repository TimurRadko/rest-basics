package com.epam.esm.dao.specification.gift;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.sort.GiftCertificateSorter;
import com.epam.esm.dao.specification.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

public final class GetGiftCertificatesByTagNameSpecification
    implements Specification<GiftCertificate> {
  private final String tagName;
  private final String sort;
  private final GiftCertificateSorter giftCertificateSorter;

  public GetGiftCertificatesByTagNameSpecification(String tagName, String sort) {
    this.tagName = tagName;
    this.sort = sort;
    this.giftCertificateSorter = new GiftCertificateSorter();
  }

  @Override
  public CriteriaQuery<GiftCertificate> getCriteriaQuery(CriteriaBuilder builder) {
    CriteriaQuery<GiftCertificate> criteria = builder.createQuery(GiftCertificate.class);
    Root<GiftCertificate> giftCertificateRoot = criteria.from(GiftCertificate.class);
    Join<GiftCertificate, Tag> giftCertificateTagJoin = giftCertificateRoot.join("tags");
    Path<String> tagNamePath = giftCertificateTagJoin.get("name");
    criteria.select(giftCertificateRoot).distinct(true);
    criteria.where(builder.equal(tagNamePath, tagName));
    if (sort == null) {
      return criteria.orderBy(builder.asc(giftCertificateRoot.get("id")));
    }
    giftCertificateSorter.sort(criteria, builder, giftCertificateRoot, sort);
    return criteria;
  }
}
