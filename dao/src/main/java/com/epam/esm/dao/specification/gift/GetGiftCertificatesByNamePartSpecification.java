package com.epam.esm.dao.specification.gift;

import com.epam.esm.dao.specification.Specification;

public final class GetGiftCertificatesByNamePartSpecification implements Specification {
  private final String name;
  private final String sort;

  private static final String QUERY =
      "SELECT gc.id, gc.name, gc.description, gc.price, "
          + "gc.duration, gc.create_date, gc.last_update_date"
          + " FROM gift_certificates gc"
          + " WHERE (gc.name ILIKE concat('%', ?, '%'))"
          + " ORDER BY"
          + " CASE WHEN ? = 'name-asc' THEN gc.name END ASC,"
          + " CASE WHEN ? = 'name-desc' THEN gc.name END DESC,"
          + " CASE WHEN ? = 'description-asc' THEN gc.description END,"
          + " CASE WHEN ? = 'description-desc' THEN gc.description END DESC,"
          + " CASE WHEN ? = 'last-update-date-desc'"
          + " THEN TO_CHAR(gc.last_update_date, 'yyyy-MM-dd''T''HH:mm''Z''') END DESC,"
          + " CASE WHEN ? = 'create-date-desc'"
          + " THEN TO_CHAR(gc.create_date, 'yyyy-MM-dd''T''HH:mm''Z''') END DESC,"
          + " gc.id ASC;";

  public GetGiftCertificatesByNamePartSpecification(String name, String sort) {
    this.name = name;
    this.sort = sort;
  }

  @Override
  public String getQuery() {
    return QUERY;
  }

  @Override
  public Object[] getArgs() {
    return new Object[] {name, sort, sort, sort, sort, sort, sort};
  }
}
