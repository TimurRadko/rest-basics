package com.epam.esm.dao.specification.gift;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.sort.GiftCertificateSorter;
import com.epam.esm.dao.specification.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public final class GetGiftCertificatesByNamePartSpecification
    implements Specification<GiftCertificate> {
  private final String name;
  private final List<String> sort;
  private final GiftCertificateSorter giftCertificateSorter;

  public GetGiftCertificatesByNamePartSpecification(String name, List<String> sort) {
    this.name = name;
    this.sort = sort;
    this.giftCertificateSorter = new GiftCertificateSorter();
  }

  @Override
  public CriteriaQuery<GiftCertificate> getCriteriaQuery(CriteriaBuilder builder) {
    CriteriaQuery<GiftCertificate> criteria = builder.createQuery(GiftCertificate.class);
    Root<GiftCertificate> giftCertificateRoot = criteria.from(GiftCertificate.class);
    criteria.select(giftCertificateRoot);
    criteria.where(
        builder.like(
            builder.lower(giftCertificateRoot.get("name")), "%" + name.toLowerCase() + "%"));
    if (sort == null) {
      return criteria.orderBy(builder.asc(giftCertificateRoot.get("id")));
    }
    giftCertificateSorter.sort(criteria, builder, giftCertificateRoot, sort);
    return criteria;
  }
}
