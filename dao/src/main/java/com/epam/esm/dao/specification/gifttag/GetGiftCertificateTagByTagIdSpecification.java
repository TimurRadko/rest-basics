package com.epam.esm.dao.specification.gifttag;

import com.epam.esm.dao.specification.Specification;

public class GetGiftCertificateTagByTagIdSpecification implements Specification {
  private final long tagId;

  private static final String QUERY =
      "SELECT id, gift_certificate_id, tag_id " + "FROM gift_certificates_tags WHERE tag_id=?;";

  public GetGiftCertificateTagByTagIdSpecification(long tagId) {
    this.tagId = tagId;
  }

  @Override
  public String getQuery() {
    return QUERY;
  }

  @Override
  public Object[] getArgs() {
    return new Object[] {tagId};
  }
}
