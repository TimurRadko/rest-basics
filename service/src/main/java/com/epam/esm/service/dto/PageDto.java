package com.epam.esm.service.dto;

public class PageDto {
  private Integer page;
  private Integer size;

  public PageDto() {}

  public PageDto(Integer page, Integer size) {
    this.page = page;
    this.size = size;
  }

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PageDto)) {
      return false;
    }

    PageDto pageDto = (PageDto) o;

    if (getPage() != null ? !getPage().equals(pageDto.getPage()) : pageDto.getPage() != null) {
      return false;
    }
    return getSize() != null ? getSize().equals(pageDto.getSize()) : pageDto.getSize() == null;
  }

  @Override
  public int hashCode() {
    int result = getPage() != null ? getPage().hashCode() : 0;
    result = 31 * result + (getSize() != null ? getSize().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "PageDto{" + "page=" + page + ", size=" + size + '}';
  }
}
