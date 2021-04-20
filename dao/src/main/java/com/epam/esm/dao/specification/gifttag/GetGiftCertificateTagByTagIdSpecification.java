package com.epam.esm.dao.specification.gifttag;

import com.epam.esm.dao.entity.GiftCertificateTag;
import com.epam.esm.dao.specification.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class GetGiftCertificateTagByTagIdSpecification
    implements Specification<GiftCertificateTag> {
  private final long tagId;

  public GetGiftCertificateTagByTagIdSpecification(long tagId) {
    this.tagId = tagId;
  }

  @Override
  public CriteriaQuery<GiftCertificateTag> getCriteriaQuery(CriteriaBuilder builder) {
    CriteriaQuery<GiftCertificateTag> criteria = builder.createQuery(GiftCertificateTag.class);
    Root<GiftCertificateTag> root = criteria.from(GiftCertificateTag.class);
    criteria.select(root);
    criteria.where(builder.equal(root.get("tagId"), tagId));
    return criteria;
  }
}
