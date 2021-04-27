package com.epam.esm.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "orders_gift_certificates")
public class OrdersGiftCertificate implements TableEntity {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne()
  @JoinColumn(name = "order_id")
  private Orders orders;

  @ManyToOne()
  @JoinColumn(name = "gift_certificate_id")
  private GiftCertificate giftCertificate;

  @Column(name = "amount")
  private Integer amount;

  public OrdersGiftCertificate() {}

  public OrdersGiftCertificate(Orders orders, GiftCertificate giftCertificate, Integer amount) {
    this.orders = orders;
    this.giftCertificate = giftCertificate;
    this.amount = amount;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public Orders getOrders() {
    return orders;
  }

  public void setOrders(Orders orders) {
    this.orders = orders;
  }

  public GiftCertificate getGiftCertificate() {
    return giftCertificate;
  }

  public void setGiftCertificate(GiftCertificate giftCertificate) {
    this.giftCertificate = giftCertificate;
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
    if (!(o instanceof OrdersGiftCertificate)) {
      return false;
    }

    OrdersGiftCertificate that = (OrdersGiftCertificate) o;

    if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) {
      return false;
    }
    if (getOrders() != null ? !getOrders().equals(that.getOrders()) : that.getOrders() != null) {
      return false;
    }
    if (getGiftCertificate() != null
        ? !getGiftCertificate().equals(that.getGiftCertificate())
        : that.getGiftCertificate() != null) {
      return false;
    }
    return getAmount() != null ? getAmount().equals(that.getAmount()) : that.getAmount() == null;
  }

  @Override
  public int hashCode() {
    int result = getId() != null ? getId().hashCode() : 0;
    result = 31 * result + (getOrders() != null ? getOrders().hashCode() : 0);
    result = 31 * result + (getGiftCertificate() != null ? getGiftCertificate().hashCode() : 0);
    result = 31 * result + (getAmount() != null ? getAmount().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "OrdersGiftCertificate{"
        + "id="
        + id
        + ", orders="
        + orders
        + ", giftCertificate="
        + giftCertificate
        + ", amount="
        + amount
        + '}';
  }
}
