package com.epam.esm.dao.specification.gifttag;

import com.epam.esm.dao.specification.Specification;

public final class GetGiftCertificateTagByGiftCertificateIdSpecification implements Specification {
  private final long giftCertificateId;
  private final long tagId;

  private static final String QUERY =
      "SELECT id, gift_certificate_id, tag_id "
          + "FROM gift_certificates_tags WHERE gift_certificate_id=? AND tag_id=?;";

  public GetGiftCertificateTagByGiftCertificateIdSpecification(long giftCertificateId, long tagId) {
    this.giftCertificateId = giftCertificateId;
    this.tagId = tagId;
  }

  @Override
  public String getQuery() {
    return QUERY;
  }

  @Override
  public Object[] getArgs() {
    return new Object[] {giftCertificateId, tagId};
  }
}
