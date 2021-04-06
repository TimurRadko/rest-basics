package com.epam.esm.dao.specification.gift;

import com.epam.esm.dao.specification.Specification;

public class GetAllGiftCertificatesSpecification implements Specification {
  private final String sort;

  private static final String QUERY =
      "SELECT id, name, description, price, "
          + "duration, create_date, last_update_date FROM gift_certificates "
          + "ORDER BY "
          + "CASE WHEN ? ='name-asc' THEN name END ASC, "
          + "CASE WHEN ? ='name-desc' THEN name END DESC, "
          + "CASE WHEN ? ='create-date-asc' THEN create_date END ASC, "
          + "CASE WHEN ? ='create-date-desc' THEN create_date END DESC, id ASC;";

  public GetAllGiftCertificatesSpecification(String sort) {
    this.sort = sort;
  }

  @Override
  public String getQuery() {
    return QUERY;
  }

  @Override
  public Object[] getArgs() {
    return new Object[] {sort, sort, sort, sort};
  }
}
