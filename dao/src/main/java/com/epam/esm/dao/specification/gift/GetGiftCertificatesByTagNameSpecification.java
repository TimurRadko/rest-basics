package com.epam.esm.dao.specification.gift;

import com.epam.esm.dao.specification.Specification;

public class GetGiftCertificatesByTagNameSpecification implements Specification {
  private final String tagName;
  private final String sort;

  private static final String QUERY =
      "SELECT gc.id, gc.name, gc.description, gc.price, gc.duration, "
          + "gc.create_date, gc.last_update_date FROM gift_certificates gc "
          + "INNER JOIN gift_certificates_tags gct ON gc.id = gct.gift_certificate_id "
          + "INNER JOIN tags t ON t.id = gct.tag_id WHERE t.name=? ORDER BY "
          + "CASE WHEN ? ='name-asc' THEN gc.name END ASC, "
          + "CASE WHEN ? ='name-desc' THEN gc.name END DESC;";

  public GetGiftCertificatesByTagNameSpecification(String tagName, String sort) {
    this.tagName = tagName;
    this.sort = sort;
  }

  @Override
  public String getQuery() {
    return QUERY;
  }

  @Override
  public Object[] getArgs() {
    return new Object[] {tagName, sort, sort};
  }
}
