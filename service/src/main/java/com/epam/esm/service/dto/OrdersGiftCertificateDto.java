package com.epam.esm.service.dto;

public class OrdersGiftCertificateDto {
  private Long giftCertificateId;
  private Integer amount;

  public OrdersGiftCertificateDto() {}

  public OrdersGiftCertificateDto(Long giftCertificateId, Integer amount) {
    this.giftCertificateId = giftCertificateId;
    this.amount = amount;
  }

  public Long getGiftCertificateId() {
    return giftCertificateId;
  }

  public void setGiftCertificateId(Long giftCertificateId) {
    this.giftCertificateId = giftCertificateId;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof OrdersGiftCertificateDto)) {
      return false;
    }

    OrdersGiftCertificateDto that = (OrdersGiftCertificateDto) o;

    if (getGiftCertificateId() != null
        ? !getGiftCertificateId().equals(that.getGiftCertificateId())
        : that.getGiftCertificateId() != null) {
      return false;
    }
    return getAmount() != null ? getAmount().equals(that.getAmount()) : that.getAmount() == null;
  }

  @Override
  public int hashCode() {
    int result = getGiftCertificateId() != null ? getGiftCertificateId().hashCode() : 0;
    result = 31 * result + (getAmount() != null ? getAmount().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "OrdersGiftCertificateDto{"
        + "giftCertificateId="
        + giftCertificateId
        + ", amount="
        + amount
        + '}';
  }
}
