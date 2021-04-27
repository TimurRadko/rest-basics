package com.epam.esm.dao.entity;

import com.epam.esm.dao.audit.AuditListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@EntityListeners(value = AuditListener.class)
public class User implements TableEntity {
  @Id
  @Column(name = "id")
  protected Long id;

  @Column(name = "login")
  private String login;

  @Column(name = "password")
  private String password;

  @Column(name = "account")
  private BigDecimal account;

  @OneToMany(mappedBy = "user")
  private Set<Order> orders;

  public User() {}

  public User(Long id, String login, String password, BigDecimal account) {
    this.id = id;
    this.login = login;
    this.password = password;
    this.account = account;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public BigDecimal getAccount() {
    return account;
  }

  public void setAccount(BigDecimal account) {
    this.account = account;
  }

  public Set<Order> getOrders() {
    return (orders == null) ? null : new HashSet<>(orders);
  }

  public void setOrders(Set<Order> orders) {
    this.orders = orders;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof User)) {
      return false;
    }

    User user = (User) o;

    if (getId() != null ? !getId().equals(user.getId()) : user.getId() != null) {
      return false;
    }
    if (getLogin() != null ? !getLogin().equals(user.getLogin()) : user.getLogin() != null) {
      return false;
    }
    if (getPassword() != null
        ? !getPassword().equals(user.getPassword())
        : user.getPassword() != null) {
      return false;
    }
    return getAccount() != null
        ? getAccount().equals(user.getAccount())
        : user.getAccount() == null;
  }

  @Override
  public int hashCode() {
    int result = getId() != null ? getId().hashCode() : 0;
    result = 31 * result + (getLogin() != null ? getLogin().hashCode() : 0);
    result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
    result = 31 * result + (getAccount() != null ? getAccount().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "User{"
        + "id="
        + id
        + ", login='"
        + login
        + '\''
        + ", password='"
        + password
        + '\''
        + ", account="
        + account
        + '}';
  }
}
