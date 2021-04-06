package com.epam.esm.dao.entity;

/** * This abstract class describes the common field on all Entities in the Database */
public abstract class AbstractEntity {
  /** * This field is id (the primary key) in all Entities located in the Database */
  protected Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  protected AbstractEntity() {}

  protected AbstractEntity(Long id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AbstractEntity)) {
      return false;
    }

    AbstractEntity that = (AbstractEntity) o;

    return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
  }

  @Override
  public int hashCode() {
    return getId() != null ? getId().hashCode() : 0;
  }

  @Override
  public String toString() {
    return "AbstractEntity{" + "id=" + id + '}';
  }
}
