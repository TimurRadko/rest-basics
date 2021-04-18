package com.epam.esm.dao.entity;

import com.epam.esm.dao.serialization.LocalDateDeserializer;
import com.epam.esm.dao.serialization.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Order extends AbstractEntity {
  private Long userId;
  private Long giftCertificateId;
  private BigDecimal cost;

  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDateTime orderDate;

  public Order() {}

  public Order(
      Long id, Long userId, Long giftCertificateId, BigDecimal cost, LocalDateTime orderDate) {
    super(id);
    this.userId = userId;
    this.giftCertificateId = giftCertificateId;
    this.cost = cost;
    this.orderDate = orderDate;
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

  public LocalDateTime getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(LocalDateTime orderDate) {
    this.orderDate = orderDate;
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
    return getOrderDate() != null
        ? getOrderDate().equals(order.getOrderDate())
        : order.getOrderDate() == null;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (getUserId() != null ? getUserId().hashCode() : 0);
    result = 31 * result + (getGiftCertificateId() != null ? getGiftCertificateId().hashCode() : 0);
    result = 31 * result + (getCost() != null ? getCost().hashCode() : 0);
    result = 31 * result + (getOrderDate() != null ? getOrderDate().hashCode() : 0);
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
        + ", orderDate="
        + orderDate
        + '}';
  }
}
