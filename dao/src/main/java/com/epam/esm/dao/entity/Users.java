package com.epam.esm.dao.entity;

import com.epam.esm.dao.entity.audit.AuditListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

import static com.epam.esm.dao.entity.Role.USER;

@Entity
@Table(name = "users")
@EntityListeners(value = AuditListener.class)
public class Users implements TableEntity {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  @Column(name = "login")
  private String login;

  @Column(name = "password")
  private String password;

  @Column(name = "balance")
  private BigDecimal balance;

  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  private Role role = USER;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
  private Set<Orders> orders;

  public Users() {}

  public Users(Long id, String login, String password, BigDecimal balance, Role role) {
    this.id = id;
    this.login = login;
    this.password = password;
    this.balance = balance;
    this.role = role;
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

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Set<Orders> getOrders() {
    return orders;
  }

  public void setOrders(Set<Orders> orders) {
    this.orders = orders;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Users users = (Users) o;

    if (!Objects.equals(id, users.id)) {
      return false;
    }
    if (!Objects.equals(login, users.login)) {
      return false;
    }
    if (!Objects.equals(password, users.password)) {
      return false;
    }
    if (!Objects.equals(balance, users.balance)) {
      return false;
    }
    if (role != users.role) {
      return false;
    }
    return Objects.equals(orders, users.orders);
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (login != null ? login.hashCode() : 0);
    result = 31 * result + (password != null ? password.hashCode() : 0);
    result = 31 * result + (balance != null ? balance.hashCode() : 0);
    result = 31 * result + (role != null ? role.hashCode() : 0);
    result = 31 * result + (orders != null ? orders.hashCode() : 0);
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
        + ", balance="
        + balance
        + ", role="
        + role
        + ", orders="
        + orders
        + '}';
  }
}
