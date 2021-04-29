package com.epam.esm.dao.specification.gift;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.sort.GiftCertificateSorter;
import com.epam.esm.dao.specification.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.List;

public final class GetAllGiftCertificatesSpecification implements Specification<GiftCertificate> {
  private final List<String> sorts;
  private final GiftCertificateSorter giftCertificateSorter;

  public GetAllGiftCertificatesSpecification(List<String> sorts) {
    this.sorts = sorts;
    this.giftCertificateSorter = new GiftCertificateSorter();
  }

  @Override
  public CriteriaQuery<GiftCertificate> getCriteriaQuery(CriteriaBuilder builder) {
    CriteriaQuery<GiftCertificate> criteria = builder.createQuery(GiftCertificate.class);
    Root<GiftCertificate> giftCertificateRoot = criteria.from(GiftCertificate.class);
    giftCertificateRoot.fetch("tags", JoinType.LEFT);
    criteria.select(giftCertificateRoot).distinct(true);
    if (sorts == null) {
      return criteria.orderBy(builder.asc(giftCertificateRoot.get("id")));
    }
    giftCertificateSorter.sort(criteria, builder, giftCertificateRoot, sorts);
    return criteria;
  }
}
