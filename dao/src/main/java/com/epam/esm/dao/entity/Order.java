package com.epam.esm.dao.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Order extends AbstractEntity {
  private Long userId;
  private Long giftCertificateId;
  private BigDecimal cost;
  private LocalDateTime createDate;

  public Order() {}

  public Order(
      Long id, Long userId, Long giftCertificateId, BigDecimal cost, LocalDateTime createDate) {
    super(id);
    this.userId = userId;
    this.giftCertificateId = giftCertificateId;
    this.cost = cost;
    this.createDate = createDate;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getGiftCertificateId() {
    return giftCertificateId;
  }

  public void setGiftCertificateId(Long giftCertificateId) {
    this.giftCertificateId = giftCertificateId;
  }

  public BigDecimal getCost() {
    return cost;
  }

  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }

  public LocalDateTime getCreateDate() {
    return createDate;
  }

  public void setCreateDate(LocalDateTime createDate) {
    this.createDate = createDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Order)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }

    Order order = (Order) o;

    if (getUserId() != null ? !getUserId().equals(order.getUserId()) : order.getUserId() != null) {
      return false;
    }
    if (getGiftCertificateId() != null
        ? !getGiftCertificateId().equals(order.getGiftCertificateId())
        : order.getGiftCertificateId() != null) {
      return false;
    }
    if (getCost() != null ? !getCost().equals(order.getCost()) : order.getCost() != null) {
      return false;
    }
    return getCreateDate() != null
        ? getCreateDate().equals(order.getCreateDate())
        : order.getCreateDate() == null;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (getUserId() != null ? getUserId().hashCode() : 0);
    result = 31 * result + (getGiftCertificateId() != null ? getGiftCertificateId().hashCode() : 0);
    result = 31 * result + (getCost() != null ? getCost().hashCode() : 0);
    result = 31 * result + (getCreateDate() != null ? getCreateDate().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Order{"
        + "id="
        + id
        + ", userId="
        + userId
        + ", giftCertificateId="
        + giftCertificateId
        + ", cost="
        + cost
        + ", createDate="
        + createDate
        + '}';
  }
}
