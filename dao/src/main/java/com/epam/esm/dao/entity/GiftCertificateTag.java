package com.epam.esm.dao.entity;

public class GiftCertificateTag extends AbstractEntity {
  private Long giftCertificateId;
  private Long tagId;

  public GiftCertificateTag() {}

  public GiftCertificateTag(Long id, Long giftCertificateId, Long tagId) {
    super(id);
    this.giftCertificateId = giftCertificateId;
    this.tagId = tagId;
  }

  public Long getGiftCertificateId() {
    return giftCertificateId;
  }

  public void setGiftCertificateId(Long giftCertificateId) {
    this.giftCertificateId = giftCertificateId;
  }

  public Long getTagId() {
    return tagId;
  }

  public void setTagId(Long tagId) {
    this.tagId = tagId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof GiftCertificateTag)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }

    GiftCertificateTag that = (GiftCertificateTag) o;

    if (getGiftCertificateId() != null
        ? !getGiftCertificateId().equals(that.getGiftCertificateId())
        : that.getGiftCertificateId() != null) {
      return false;
    }
    return getTagId() != null ? getTagId().equals(that.getTagId()) : that.getTagId() == null;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (getGiftCertificateId() != null ? getGiftCertificateId().hashCode() : 0);
    result = 31 * result + (getTagId() != null ? getTagId().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "GiftCertificateTag{"
        + "id="
        + id
        + ", giftCertificateId="
        + giftCertificateId
        + ", tagId="
        + tagId
        + '}';
  }
}
