package com.epam.esm.dao.specification.gift;

import com.epam.esm.dao.specification.Specification;

public class GetGiftCertificatesByDescriptionPartSpecification implements Specification {
  private final String description;
  private final String sort;

  private static final String QUERY =
      "SELECT gc.id, gc.name, gc.description, gc.price, "
          + "gc.duration, gc.create_date, gc.last_update_date"
          + " FROM gift_certificates gc"
          + " WHERE (gc.description ILIKE concat('%', ?, '%'))"
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

  public GetGiftCertificatesByDescriptionPartSpecification(String description, String sort) {
    this.description = description;
    this.sort = sort;
  }

  @Override
  public String getQuery() {
    return QUERY;
  }

  @Override
  public Object[] getArgs() {
    return new Object[] {description, sort, sort, sort, sort, sort, sort};
  }
}
