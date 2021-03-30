package com.epam.esm.service.dto;

import com.epam.esm.persistence.entity.Entity;
import com.epam.esm.persistence.entity.GiftCertificate;
import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.persistence.serialization.LocalDateDeserializer;
import com.epam.esm.persistence.serialization.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public class GiftCertificateDto implements Entity {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate createDate;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate lastUpdateDate;

    private Set<Tag> tags;

    public GiftCertificateDto() {
    }

    public GiftCertificateDto(GiftCertificate giftCertificate, Set<Tag> tags) {
        this.id = giftCertificate.getId();
        this.name = giftCertificate.getName();
        this.description = giftCertificate.getDescription();
        this.price = giftCertificate.getPrice();
        this.createDate = giftCertificate.getCreateDate();
        this.lastUpdateDate = giftCertificate.getLastUpdateDate();
        this.tags = tags;
    }

    public GiftCertificateDto(Long id,
                              String name,
                              String description,
                              BigDecimal price,
                              int duration,
                              LocalDate createDate,
                              LocalDate lastUpdateDate,
                              Set<Tag> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDate lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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

        if (getDuration() != that.getDuration()) {
            return false;
        }
        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) {
            return false;
        }
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) {
            return false;
        }
        if (getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null) {
            return false;
        }
        if (getPrice() != null ? !getPrice().equals(that.getPrice()) : that.getPrice() != null) {
            return false;
        }
        if (getCreateDate() != null ? !getCreateDate().equals(that.getCreateDate()) : that.getCreateDate() != null) {
            return false;
        }
        if (getLastUpdateDate() != null ? !getLastUpdateDate().equals(that.getLastUpdateDate()) : that.getLastUpdateDate() != null) {
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
        result = 31 * result + getDuration();
        result = 31 * result + (getCreateDate() != null ? getCreateDate().hashCode() : 0);
        result = 31 * result + (getLastUpdateDate() != null ? getLastUpdateDate().hashCode() : 0);
        result = 31 * result + (getTags() != null ? getTags().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GiftCertificateDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", tags=" + tags +
                '}';
    }
}