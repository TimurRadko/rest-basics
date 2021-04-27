package com.epam.esm.dao.specification.gift;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.specification.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public final class GetGiftCertificatesBySeveralIdsSpecification
    implements Specification<GiftCertificate> {
  private final List<Long> ids;

  public GetGiftCertificatesBySeveralIdsSpecification(List<Long> ids) {
    this.ids = ids;
  }

  @Override
  public CriteriaQuery<GiftCertificate> getCriteriaQuery(CriteriaBuilder builder) {
    CriteriaQuery<GiftCertificate> criteria = builder.createQuery(GiftCertificate.class);
    Root<GiftCertificate> giftCertificateRoot = criteria.from(GiftCertificate.class);
    giftCertificateRoot.fetch("tags");
    List<Predicate> predicates = new ArrayList<>();
    for (Long id : ids) {
      Predicate predicate = builder.equal(giftCertificateRoot.get("id"), id);
      predicates.add(predicate);
    }
    Predicate[] arrayPredicates = new Predicate[predicates.size()];
    predicates.toArray(arrayPredicates);
    criteria.where(builder.and(arrayPredicates));
    return criteria;
  }
}
