package com.epam.esm.dao.entity;

import com.epam.esm.dao.serialization.LocalDateDeserializer;
import com.epam.esm.dao.serialization.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order implements TableEntity {
  @Id
  @Column(name = "id")
  protected Long id;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "cost")
  private BigDecimal cost;

  @Column(name = "order_date")
  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDateTime orderDate;

  public Order() {}

  public Order(Long id, Long userId, BigDecimal cost, LocalDateTime orderDate) {
    this.id = id;
    this.userId = userId;
    this.cost = cost;
    this.orderDate = orderDate;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
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

    Order order = (Order) o;

    if (getId() != null ? !getId().equals(order.getId()) : order.getId() != null) {
      return false;
    }
    if (getUserId() != null ? !getUserId().equals(order.getUserId()) : order.getUserId() != null) {
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
    int result = getId() != null ? getId().hashCode() : 0;
    result = 31 * result + (getUserId() != null ? getUserId().hashCode() : 0);
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
        + ", cost="
        + cost
        + ", orderDate="
        + orderDate
        + '}';
  }
}
