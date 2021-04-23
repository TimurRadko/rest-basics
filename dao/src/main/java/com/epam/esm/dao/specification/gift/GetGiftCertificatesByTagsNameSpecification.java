package com.epam.esm.dao.specification.gift;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.specification.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.List;

public class GetGiftCertificatesByTagsNameSpecification implements Specification<GiftCertificate> {
  private final List<String> tagNames;

  public GetGiftCertificatesByTagsNameSpecification(List<String> tagNames) {
    this.tagNames = tagNames;
  }

  @Override
  public CriteriaQuery<GiftCertificate> getCriteriaQuery(CriteriaBuilder builder) {
    CriteriaQuery<GiftCertificate> criteria = builder.createQuery(GiftCertificate.class);
    Root<GiftCertificate> giftCertificateRoot = criteria.from(GiftCertificate.class);
    List<Predicate> predicates = new ArrayList<>();
    for (String tagName : tagNames) {
      Subquery<GiftCertificate> subQuery = build(criteria, tagName, builder);
      Predicate predicate = builder.in(giftCertificateRoot).value(subQuery);
      predicates.add(predicate);
    }
    Predicate[] arrayPredicates = new Predicate[predicates.size()];
    predicates.toArray(arrayPredicates);
    criteria.where(builder.and(arrayPredicates));
    return criteria;
  }

  private Subquery<GiftCertificate> build(
      CriteriaQuery<GiftCertificate> criteria, String tagName, CriteriaBuilder builder) {
    Subquery<GiftCertificate> subQuery = criteria.subquery(GiftCertificate.class);
    Root<GiftCertificate> subQueryGiftCertificateRoot = subQuery.from(GiftCertificate.class);
    Join<GiftCertificate, Tag> giftCertificateTagJoin = subQueryGiftCertificateRoot.join("tags");
    Path<String> tagNamePath = giftCertificateTagJoin.get("name");
    subQuery.select(subQueryGiftCertificateRoot).distinct(true);
    subQuery.where(builder.equal(tagNamePath, tagName));
    return subQuery;
  }
}
