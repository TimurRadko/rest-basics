package com.epam.esm.dao.specification.tag;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.GiftCertificateTag;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.specification.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

public final class GetAllTagsByGiftCertificatesIdSpecification implements Specification<Tag> {
  private final long id;

  private static final String QUERY =
      "SELECT t.id, t.name FROM tags t "
          + "JOIN gift_certificates_tags gct "
          + "ON t.id=gct.tag_id WHERE gct.gift_certificate_id=?;";

  public GetAllTagsByGiftCertificatesIdSpecification(long id) {
    this.id = id;
  }

  @Override
  public CriteriaQuery<Tag> getCriteriaQuery(CriteriaBuilder builder) {
    CriteriaQuery<Tag> criteria = builder.createQuery(Tag.class);
    Root<Tag> root = criteria.from(Tag.class);

//    Join<Tag, GiftCertificateTag> join = root.join("giftCertificateTag");
//    join.on(builder.equal(root.get("id"), join.get("tagId")));
//
//    return criteria.multiselect(join);
    return criteria.where(root.get("giftCertificates"));
  }
}
