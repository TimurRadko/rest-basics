package com.epam.esm.service.dto;

import java.math.BigDecimal;

public class GiftCertificatePriceDto {
  private BigDecimal price;

  public GiftCertificatePriceDto() {}

  public GiftCertificatePriceDto(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof GiftCertificatePriceDto)) {
      return false;
    }

    GiftCertificatePriceDto that = (GiftCertificatePriceDto) o;

    return getPrice() != null ? getPrice().equals(that.getPrice()) : that.getPrice() == null;
  }

  @Override
  public int hashCode() {
    return getPrice() != null ? getPrice().hashCode() : 0;
  }

  @Override
  public String toString() {
    return "GiftCertificatePriceDto{" + "price=" + price + '}';
  }
}
