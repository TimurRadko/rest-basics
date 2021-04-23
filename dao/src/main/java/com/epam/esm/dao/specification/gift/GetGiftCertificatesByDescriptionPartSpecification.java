package com.epam.esm.dao.specification.gift;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.sort.GiftCertificateSorter;
import com.epam.esm.dao.specification.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public final class GetGiftCertificatesByDescriptionPartSpecification
    implements Specification<GiftCertificate> {
  private final String description;
  private final List<String> sorts;
  private final GiftCertificateSorter giftCertificateSorter;

  public GetGiftCertificatesByDescriptionPartSpecification(String description, List<String> sorts) {
    this.description = description;
    this.sorts = sorts;
    this.giftCertificateSorter = new GiftCertificateSorter();
  }

  @Override
  public CriteriaQuery<GiftCertificate> getCriteriaQuery(CriteriaBuilder builder) {
    CriteriaQuery<GiftCertificate> criteria = builder.createQuery(GiftCertificate.class);
    Root<GiftCertificate> giftCertificateRoot = criteria.from(GiftCertificate.class);
    criteria.select(giftCertificateRoot);
    criteria.where(
        builder.like(
            builder.lower(giftCertificateRoot.get("description")),
            "%" + description.toLowerCase() + "%"));
    if (sorts == null) {
      return criteria.orderBy(builder.asc(giftCertificateRoot.get("id")));
    }
    giftCertificateSorter.sort(criteria, builder, giftCertificateRoot, sorts);
    return criteria;
  }
}
