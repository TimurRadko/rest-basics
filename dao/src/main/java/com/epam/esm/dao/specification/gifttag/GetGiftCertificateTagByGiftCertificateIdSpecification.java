package com.epam.esm.dao.specification.gifttag;

import com.epam.esm.dao.entity.GiftCertificateTag;
import com.epam.esm.dao.specification.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public final class GetGiftCertificateTagByGiftCertificateIdSpecification
    implements Specification<GiftCertificateTag> {
  private final long giftCertificateId;
  private final Object tagId;

  private static final String QUERY =
      "SELECT id, gift_certificate_id, tag_id "
          + "FROM gift_certificates_tags WHERE gift_certificate_id=? AND tag_id=?;";

  public GetGiftCertificateTagByGiftCertificateIdSpecification(
      long giftCertificateId, Object tagId) {
    this.giftCertificateId = giftCertificateId;
    this.tagId = tagId;
  }

  @Override
  public CriteriaQuery<GiftCertificateTag> getCriteriaQuery(CriteriaBuilder builder) {
    CriteriaQuery<GiftCertificateTag> criteria = builder.createQuery(GiftCertificateTag.class);
    Root<GiftCertificateTag> root = criteria.from(GiftCertificateTag.class);
    criteria.multiselect(root);
    builder.and(
        builder.equal(root.get("giftCertificateId"), giftCertificateId),
        builder.equal(root.get("tagId"), tagId));
    return criteria;
  }
}
