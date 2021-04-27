package com.epam.esm.dao.entity;

import com.epam.esm.dao.audit.Action;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_history_operations")
public class AuditHistory implements TableEntity {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "action")
  private String action;

  @Column(name = "content")
  private String content;

  @Column(name = "create_date")
  private LocalDateTime createDate;

  public AuditHistory() {}

  public AuditHistory(TableEntity tableEntity, Action action) {
    this.action = action.value();
    this.content = tableEntity.toString();
    this.createDate = LocalDateTime.now();
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public LocalDateTime getCreateDate() {
    return createDate;
  }

  public void setCreateDate(LocalDateTime createDate) {
    this.createDate = createDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AuditHistory)) {
      return false;
    }

    AuditHistory that = (AuditHistory) o;

    if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) {
      return false;
    }
    if (getAction() != null ? !getAction().equals(that.getAction()) : that.getAction() != null) {
      return false;
    }
    if (getContent() != null
        ? !getContent().equals(that.getContent())
        : that.getContent() != null) {
      return false;
    }
    return getCreateDate() != null
        ? getCreateDate().equals(that.getCreateDate())
        : that.getCreateDate() == null;
  }

  @Override
  public int hashCode() {
    int result = getId() != null ? getId().hashCode() : 0;
    result = 31 * result + (getAction() != null ? getAction().hashCode() : 0);
    result = 31 * result + (getContent() != null ? getContent().hashCode() : 0);
    result = 31 * result + (getCreateDate() != null ? getCreateDate().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "AuditHistory{"
        + "id="
        + id
        + ", action='"
        + action
        + '\''
        + ", content='"
        + content
        + '\''
        + ", createDate="
        + createDate
        + '}';
  }
}
