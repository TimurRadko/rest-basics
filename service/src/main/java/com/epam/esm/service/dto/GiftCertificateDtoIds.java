package com.epam.esm.service.dto;

import java.util.List;
import java.util.Objects;

public class GiftCertificateDtoIds {
  private List<Long> giftCertificateDtoIds;

  public GiftCertificateDtoIds() {}

  public GiftCertificateDtoIds(List<Long> giftCertificateDtos) {
    this.giftCertificateDtoIds = giftCertificateDtos;
  }

  public List<Long> getGiftCertificateDtoIds() {
    return giftCertificateDtoIds;
  }

  public void setGiftCertificateDtoIds(List<Long> giftCertificateDtoIds) {
    this.giftCertificateDtoIds = giftCertificateDtoIds;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GiftCertificateDtoIds that = (GiftCertificateDtoIds) o;
    return Objects.equals(giftCertificateDtoIds, that.giftCertificateDtoIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(giftCertificateDtoIds);
  }

  @Override
  public String toString() {
    return "GiftCertificateDtoIds{" + "giftCertificateDtos=" + giftCertificateDtoIds + '}';
  }
}
