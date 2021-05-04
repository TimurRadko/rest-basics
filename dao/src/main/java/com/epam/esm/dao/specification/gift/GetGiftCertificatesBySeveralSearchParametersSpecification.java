package com.epam.esm.dao.specification.gift;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.sort.GiftCertificateSorter;
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

public final class GetGiftCertificatesBySeveralSearchParametersSpecification
    implements Specification<GiftCertificate> {
  private final String name;
  private final String description;
  private final List<String> sorts;
  private final List<String> tagNames;
  private final GiftCertificateSorter giftCertificateSorter;

  public GetGiftCertificatesBySeveralSearchParametersSpecification(
      String name, String description, List<String> tagNames, List<String> sorts) {
    this.name = name;
    this.description = description;
    this.tagNames = tagNames;
    this.sorts = sorts;
    this.giftCertificateSorter = new GiftCertificateSorter();
  }

  @Override
  public CriteriaQuery<GiftCertificate> getCriteriaQuery(CriteriaBuilder builder) {
    CriteriaQuery<GiftCertificate> criteria = builder.createQuery(GiftCertificate.class);
    Root<GiftCertificate> giftCertificateRoot = criteria.from(GiftCertificate.class);
    List<Predicate> predicates = new ArrayList<>();
    addPartNamePredicate(builder, giftCertificateRoot, predicates);
    addPartDescriptionPredicate(builder, giftCertificateRoot, predicates);
    addTagsNamePredicates(builder, criteria, giftCertificateRoot, predicates);

    Predicate[] arrayPredicates = new Predicate[predicates.size()];
    predicates.toArray(arrayPredicates);
    criteria.where(builder.and(arrayPredicates));
    if (sorts == null) {
      return criteria.orderBy(builder.asc(giftCertificateRoot.get("id")));
    }
    giftCertificateSorter.sort(builder, sorts);
    return criteria;
  }

  private void addTagsNamePredicates(
      CriteriaBuilder builder,
      CriteriaQuery<GiftCertificate> criteria,
      Root<GiftCertificate> giftCertificateRoot,
      List<Predicate> predicates) {
    if (tagNames != null) {
      for (String tagName : tagNames) {
        Subquery<GiftCertificate> subQuery = buildTagNamesPredicates(criteria, tagName, builder);
        Predicate predicate = builder.in(giftCertificateRoot).value(subQuery);
        predicates.add(predicate);
      }
    }
  }

  private void addPartDescriptionPredicate(
      CriteriaBuilder builder,
      Root<GiftCertificate> giftCertificateRoot,
      List<Predicate> predicates) {
    if (description != null && description.trim().length() != 0) {
      predicates.add(
          builder.like(
              builder.lower(giftCertificateRoot.get("description")),
              "%" + description.toLowerCase() + "%"));
    }
  }

  private void addPartNamePredicate(
      CriteriaBuilder builder,
      Root<GiftCertificate> giftCertificateRoot,
      List<Predicate> predicates) {
    if (name != null && name.trim().length() != 0) {
      predicates.add(
          builder.like(
              builder.lower(giftCertificateRoot.get("name")), "%" + name.toLowerCase() + "%"));
    }
  }

  private Subquery<GiftCertificate> buildTagNamesPredicates(
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
