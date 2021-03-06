package com.epam.esm.dao.entity;

import com.epam.esm.dao.entity.audit.AuditListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tags")
@EntityListeners(value = AuditListener.class)
public class Tag implements TableEntity {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
  private List<GiftCertificate> giftCertificates;

  public Tag() {}

  public Tag(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<GiftCertificate> getGiftCertificates() {
    return (giftCertificates == null) ? null : new ArrayList<>(giftCertificates);
  }

  public void setGiftCertificates(List<GiftCertificate> giftCertificates) {
    this.giftCertificates = giftCertificates;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Tag)) {
      return false;
    }

    Tag tag = (Tag) o;

    if (getId() != null ? !getId().equals(tag.getId()) : tag.getId() != null) {
      return false;
    }
    return getName() != null ? getName().equals(tag.getName()) : tag.getName() == null;
  }

  @Override
  public int hashCode() {
    int result = getId() != null ? getId().hashCode() : 0;
    result = 31 * result + (getName() != null ? getName().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Tag{" + "id=" + id + ", name='" + name + '\'' + '}';
  }
}
