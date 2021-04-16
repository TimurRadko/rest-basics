package com.epam.esm.dao.entity;

import java.math.BigDecimal;

public class User extends AbstractEntity {
  private String login;
  private String password;
  private BigDecimal account;

  public User() {}

  public User(Long id, String login, String password, BigDecimal account) {
    super(id);
    this.login = login;
    this.password = password;
    this.account = account;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof User)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }

    User user = (User) o;

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
    int result = super.hashCode();
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
