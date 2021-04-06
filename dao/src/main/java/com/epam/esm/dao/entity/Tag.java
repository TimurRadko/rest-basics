package com.epam.esm.dao.entity;

public class Tag extends AbstractEntity {
  private String name;

  public Tag() {}

  public Tag(Long id, String name) {
    super(id);
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Tag)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }

    Tag tag = (Tag) o;

    return getName() != null ? getName().equals(tag.getName()) : tag.getName() == null;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (getName() != null ? getName().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Tag{" + "id=" + id + ", name='" + name + '\'' + '}';
  }
}
