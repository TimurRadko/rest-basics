package com.epam.esm.service.dto;

import com.epam.esm.dao.serialization.LocalDateDeserializer;
import com.epam.esm.dao.serialization.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {
  private Long id;
  private String name;
  private String description;
  private BigDecimal price;
  private Integer duration;

  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDateTime createDate;

  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDateTime lastUpdateDate;

  private Set<TagDto> tags;

  public GiftCertificateDto() {}

  public GiftCertificateDto(
      Long id,
      String name,
      String description,
      BigDecimal price,
      Integer duration,
      LocalDateTime createDate,
      LocalDateTime lastUpdateDate,
      Set<TagDto> tags) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.duration = duration;
    this.createDate = createDate;
    this.lastUpdateDate = lastUpdateDate;
    this.tags = tags;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Set<TagDto> getTags() {
    return (tags == null) ? null : new HashSet<>(tags);
  }

  public void setTags(Set<TagDto> tags) {
    this.tags = tags;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof GiftCertificateDto)) {
      return false;
    }

    GiftCertificateDto that = (GiftCertificateDto) o;

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
        : that.getCreateDate() != null) {
      return false;
    }
    if (getLastUpdateDate() != null
        ? !getLastUpdateDate().equals(that.getLastUpdateDate())
        : that.getLastUpdateDate() != null) {
      return false;
    }
    return getTags() != null ? getTags().equals(that.getTags()) : that.getTags() == null;
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
    result = 31 * result + (getTags() != null ? getTags().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "GiftCertificateDto{"
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
        + ", tagDtos="
        + tags
        + '}';
  }
}
