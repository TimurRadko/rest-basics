package com.epam.esm.dao.specification.gift;

import com.epam.esm.dao.specification.Specification;

public class GetGiftCertificatesByOrderIdSpecification implements Specification {
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
  public String getQuery() {
    return QUERY;
  }

  @Override
  public Object[] getArgs() {
    return new Object[] {id};
  }
}
