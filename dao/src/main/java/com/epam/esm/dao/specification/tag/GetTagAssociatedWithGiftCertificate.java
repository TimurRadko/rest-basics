package com.epam.esm.dao.specification.tag;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.specification.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

public final class GetTagAssociatedWithGiftCertificate implements Specification<Tag> {
  private final long tagId;
  private final long giftCertificateId;

  public GetTagAssociatedWithGiftCertificate(long tagId, long giftCertificateId) {
    this.tagId = tagId;
    this.giftCertificateId = giftCertificateId;
  }

  @Override
  public CriteriaQuery<Tag> getCriteriaQuery(CriteriaBuilder builder) {
    CriteriaQuery<Tag> criteria = builder.createQuery(Tag.class);
    Root<Tag> tagRoot = criteria.from(Tag.class);
    Join<Tag, GiftCertificate> tagsJoin = tagRoot.join("giftCertificates", JoinType.LEFT);
    Path<String> giftCertificateNamePath = tagsJoin.get("id");
    criteria.select(tagRoot).distinct(true);
    criteria.where(
        builder.and(
            builder.equal(tagRoot.get("id"), tagId),
            builder.equal(giftCertificateNamePath, giftCertificateId)));
    return criteria;
  }
}
