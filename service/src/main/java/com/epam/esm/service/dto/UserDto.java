package com.epam.esm.service.dto;

import com.epam.esm.dao.entity.User;

import java.math.BigDecimal;

public class UserDto {
  private Long id;
  private String login;
  private BigDecimal account;

  public UserDto() {}

  public UserDto(Long id, String login, BigDecimal account) {
    this.id = id;
    this.login = login;
    this.account = account;
  }

  public UserDto(User user) {
    this.id = user.getId();
    this.login = user.getLogin();
    this.account = user.getAccount();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
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
    if (!(o instanceof UserDto)) {
      return false;
    }

    UserDto userDto = (UserDto) o;

    if (getId() != null ? !getId().equals(userDto.getId()) : userDto.getId() != null) {
      return false;
    }
    if (getLogin() != null ? !getLogin().equals(userDto.getLogin()) : userDto.getLogin() != null) {
      return false;
    }
    return getAccount() != null
        ? getAccount().equals(userDto.getAccount())
        : userDto.getAccount() == null;
  }

  @Override
  public int hashCode() {
    int result = getId() != null ? getId().hashCode() : 0;
    result = 31 * result + (getLogin() != null ? getLogin().hashCode() : 0);
    result = 31 * result + (getAccount() != null ? getAccount().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "UserDto{" + "id=" + id + ", login='" + login + '\'' + ", account=" + account + '}';
  }
}
