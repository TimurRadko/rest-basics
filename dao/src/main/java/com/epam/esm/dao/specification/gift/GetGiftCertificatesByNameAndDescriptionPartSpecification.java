package com.epam.esm.dao.specification.gift;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.sort.GiftCertificateSorter;
import com.epam.esm.dao.specification.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class GetGiftCertificatesByNameAndDescriptionPartSpecification
    implements Specification<GiftCertificate> {
  private final String name;
  private final String description;
  private final List<String> sorts;
  private final GiftCertificateSorter giftCertificateSorter;

  public GetGiftCertificatesByNameAndDescriptionPartSpecification(
      String name,
      String description,
      List<String> sorts,
      GiftCertificateSorter giftCertificateSorter) {
    this.name = name;
    this.description = description;
    this.sorts = sorts;
    this.giftCertificateSorter = giftCertificateSorter;
  }

  @Override
  public CriteriaQuery<GiftCertificate> getCriteriaQuery(CriteriaBuilder builder) {
//      CriteriaQuery<GiftCertificate> criteria = builder.createQuery(GiftCertificate.class);
//      Root<GiftCertificate> giftCertificateRoot = criteria.from(GiftCertificate.class);
//      criteria.select(giftCertificateRoot);
//
//      criteria.where(
//              builder.like(
//                      builder.lower(giftCertificateRoot.get("name")), "%" + name.toLowerCase() + "%"));
//
//      if (sorts == null) {
//          return criteria.orderBy(builder.asc(giftCertificateRoot.get("id")));
//      }
//      giftCertificateSorter.sort(criteria, builder, giftCertificateRoot, sorts);
//      return criteria;
      return null;
  }
}
