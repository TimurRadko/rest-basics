package com.epam.esm.service.dto;

import com.epam.esm.dao.serialization.LocalDateDeserializer;
import com.epam.esm.dao.serialization.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public class OrderDto {
  private Long id;
  private BigDecimal cost;

  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDateTime orderDate;

  private Long userId;

  private Set<GiftCertificateDto> giftCertificates;

  public OrderDto() {}

  public OrderDto(
      Long id,
      BigDecimal cost,
      LocalDateTime orderDate,
      Long userId,
      Set<GiftCertificateDto> giftCertificates) {
    this.id = id;
    this.cost = cost;
    this.orderDate = orderDate;
    this.userId = userId;
    this.giftCertificates = giftCertificates;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Set<GiftCertificateDto> getGiftCertificates() {
    return giftCertificates;
  }

  public void setGiftCertificates(Set<GiftCertificateDto> giftCertificates) {
    this.giftCertificates = giftCertificates;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof OrderDto)) {
      return false;
    }

    OrderDto orderDto = (OrderDto) o;

    if (getId() != null ? !getId().equals(orderDto.getId()) : orderDto.getId() != null) {
      return false;
    }
    if (getCost() != null ? !getCost().equals(orderDto.getCost()) : orderDto.getCost() != null) {
      return false;
    }
    if (getOrderDate() != null
        ? !getOrderDate().equals(orderDto.getOrderDate())
        : orderDto.getOrderDate() != null) {
      return false;
    }
    if (getUserId() != null
        ? !getUserId().equals(orderDto.getUserId())
        : orderDto.getUserId() != null) {
      return false;
    }
    return getGiftCertificates() != null
        ? getGiftCertificates().equals(orderDto.getGiftCertificates())
        : orderDto.getGiftCertificates() == null;
  }

  @Override
  public int hashCode() {
    int result = getId() != null ? getId().hashCode() : 0;
    result = 31 * result + (getCost() != null ? getCost().hashCode() : 0);
    result = 31 * result + (getOrderDate() != null ? getOrderDate().hashCode() : 0);
    result = 31 * result + (getUserId() != null ? getUserId().hashCode() : 0);
    result = 31 * result + (getGiftCertificates() != null ? getGiftCertificates().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "OrderDto{"
        + "id="
        + id
        + ", cost="
        + cost
        + ", orderDate="
        + orderDate
        + ", userId="
        + userId
        + ", giftCertificates="
        + giftCertificates
        + '}';
  }
}
