package com.epam.esm.dao.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class GiftCertificate extends AbstractEntity {
  private String name;
  private String description;
  private BigDecimal price;
  private Integer duration;
  private LocalDateTime createDate;
  private LocalDateTime lastUpdateDate;

  public GiftCertificate() {}

  public GiftCertificate(
      Long id,
      String name,
      String description,
      BigDecimal price,
      int duration,
      LocalDateTime createDate,
      LocalDateTime lastUpdateDate) {
    super(id);
    this.name = name;
    this.description = description;
    this.price = price;
    this.duration = duration;
    this.createDate = createDate;
    this.lastUpdateDate = lastUpdateDate;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Integer getDuration() {
    return duration;
  }

  public void setDuration(Integer duration) {
    this.duration = duration;
  }

  public LocalDateTime getCreateDate() {
    return createDate;
  }

  public void setCreateDate(LocalDateTime createDate) {
    this.createDate = createDate;
  }

  public LocalDateTime getLastUpdateDate() {
    return lastUpdateDate;
  }

  public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
    this.lastUpdateDate = lastUpdateDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof GiftCertificate)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }

    GiftCertificate that = (GiftCertificate) o;

    if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) {
      return false;
    }
    if (getDescription() != null
        ? !getDescription().equals(that.getDescription())
        : that.getDescription() != null) {
      return false;
    }
    if (getPrice() != null ? !getPrice().equals(that.getPrice()) : that.getPrice() != null) {
      return false;
    }
    if (getDuration() != null
        ? !getDuration().equals(that.getDuration())
        : that.getDuration() != null) {
      return false;
    }
    if (getCreateDate() != null
        ? !getCreateDate().equals(that.getCreateDate())
        : that.getCreateDate() != null) {
      return false;
    }
    return getLastUpdateDate() != null
        ? getLastUpdateDate().equals(that.getLastUpdateDate())
        : that.getLastUpdateDate() == null;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (getName() != null ? getName().hashCode() : 0);
    result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
    result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
    result = 31 * result + (getDuration() != null ? getDuration().hashCode() : 0);
    result = 31 * result + (getCreateDate() != null ? getCreateDate().hashCode() : 0);
    result = 31 * result + (getLastUpdateDate() != null ? getLastUpdateDate().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "GiftCertificate{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", description='"
        + description
        + '\''
        + ", price="
        + price
        + ", duration="
        + duration
        + ", createDate="
        + createDate
        + ", lastUpdateDate="
        + lastUpdateDate
        + '}';
  }
}
