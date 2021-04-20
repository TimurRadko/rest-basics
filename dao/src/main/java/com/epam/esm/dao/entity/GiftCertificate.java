package com.epam.esm.dao.entity;

import com.epam.esm.dao.serialization.LocalDateDeserializer;
import com.epam.esm.dao.serialization.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "gift_certificates")
public class GiftCertificate implements TableEntity {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.TABLE)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "price")
  private BigDecimal price;

  @Column(name = "duration")
  private Integer duration;

  @Column(name = "create_date")
  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDateTime createDate;

  @Column(name = "last_update_date")
  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDateTime lastUpdateDate;

  //      @ManyToMany(fetch = FetchType.EAGER)
  //      @JoinTable(
  //          name = "gift_certificates_tags",
  //          joinColumns = @JoinColumn(name = "gift_certificate_id"),
  //          inverseJoinColumns = @JoinColumn(name = "tag_id"))
  @ManyToMany
  @JoinTable(
      name = "gift_certificates_tags",
      joinColumns = @JoinColumn(name = "gift_certificate_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private Set<Tag> tags;

  public GiftCertificate() {}

  public GiftCertificate(
      Long id,
      String name,
      String description,
      BigDecimal price,
      Integer duration,
      LocalDateTime createDate,
      LocalDateTime lastUpdateDate) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.duration = duration;
    this.createDate = createDate;
    this.lastUpdateDate = lastUpdateDate;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public Set<Tag> getTags() {
    return tags;
  }

  public void setTags(Set<Tag> tags) {
    this.tags = tags;
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

    GiftCertificate that = (GiftCertificate) o;

    if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) {
      return false;
    }
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
        : that.getCreateDate() != null) return false;
    return getLastUpdateDate() != null
        ? getLastUpdateDate().equals(that.getLastUpdateDate())
        : that.getLastUpdateDate() == null;
  }

  @Override
  public int hashCode() {
    int result = getId() != null ? getId().hashCode() : 0;
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
