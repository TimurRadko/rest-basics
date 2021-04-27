package com.epam.esm.dao.entity;

import com.epam.esm.dao.entity.audit.AuditListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "orders")
@EntityListeners(value = AuditListener.class)
public class Orders implements TableEntity {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  @Column(name = "cost")
  private BigDecimal cost;

  @Column(name = "order_date")
  private LocalDateTime orderDate;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "orders_gift_certificates",
      joinColumns = @JoinColumn(name = "order_id"),
      inverseJoinColumns = @JoinColumn(name = "gift_certificate_id"))
  private List<GiftCertificate> giftCertificates;

  @ManyToOne()
  @JoinColumn(name = "user_id")
  private User user;

  public Orders() {}

  public Orders(Long id, BigDecimal cost, LocalDateTime orderDate) {
    this.id = id;
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

  public List<GiftCertificate> getGiftCertificates() {
    return (giftCertificates == null) ? null : new ArrayList<>(giftCertificates);
  }

  public void setGiftCertificates(List<GiftCertificate> giftCertificates) {
    this.giftCertificates = giftCertificates;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Orders)) {
      return false;
    }

    Orders orders = (Orders) o;

    if (getId() != null ? !getId().equals(orders.getId()) : orders.getId() != null) {
      return false;
    }
    if (getCost() != null ? !getCost().equals(orders.getCost()) : orders.getCost() != null) {
      return false;
    }
    return getOrderDate() != null
        ? getOrderDate().equals(orders.getOrderDate())
        : orders.getOrderDate() == null;
  }

  @Override
  public int hashCode() {
    int result = getId() != null ? getId().hashCode() : 0;
    result = 31 * result + (getCost() != null ? getCost().hashCode() : 0);
    result = 31 * result + (getOrderDate() != null ? getOrderDate().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Order{" + "id=" + id + ", cost=" + cost + ", orderDate=" + orderDate + '}';
  }
}
