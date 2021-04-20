package com.epam.esm.dao.specification.gift;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.specification.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class GetGiftCertificatesByOrderIdSpecification implements Specification<GiftCertificate> {
  private final long id;
  private static final String QUERY =
      "SELECT gc.id, gc.name, gc.description, gc.price, gc.duration,"
          + " gc.create_date, gc.last_update_date FROM gift_certificates gc"
          + " JOIN orders_gift_certificates ogc"
          + " ON gc.id = ogc.gift_certificate_id"
          + " WHERE ogc.order_id =?;";

  public GetGiftCertificatesByOrderIdSpecification(long id) {
    this.id = id;
  }

  @Override
  public CriteriaQuery<GiftCertificate> getCriteriaQuery(CriteriaBuilder builder) {
    return null;
  }
}
