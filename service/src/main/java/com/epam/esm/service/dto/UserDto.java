package com.epam.esm.service.dto;

import com.epam.esm.dao.entity.User;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class UserDto {
  private Long id;
  private String login;
  private BigDecimal account;
  private List<OrderDto> orders;

  public UserDto() {}

  public UserDto(Long id, String login, BigDecimal account, List<OrderDto> orders) {
    this.id = id;
    this.login = login;
    this.account = account;
    this.orders = orders;
  }

  public UserDto(User user, List<OrderDto> orders) {
    this.id = user.getId();
    this.login = user.getLogin();
    this.account = user.getAccount();
    this.orders = orders;
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

  public List<OrderDto> getOrders() {
    return Collections.unmodifiableList(orders);
  }

  public void setOrders(List<OrderDto> orders) {
    this.orders = orders;
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
    if (getAccount() != null
        ? !getAccount().equals(userDto.getAccount())
        : userDto.getAccount() != null) {
      return false;
    }
    return getOrders() != null
        ? getOrders().equals(userDto.getOrders())
        : userDto.getOrders() == null;
  }

  @Override
  public int hashCode() {
    int result = getId() != null ? getId().hashCode() : 0;
    result = 31 * result + (getLogin() != null ? getLogin().hashCode() : 0);
    result = 31 * result + (getAccount() != null ? getAccount().hashCode() : 0);
    result = 31 * result + (getOrders() != null ? getOrders().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "UserDto{"
        + "id="
        + id
        + ", login='"
        + login
        + '\''
        + ", account="
        + account
        + ", orderDtos="
        + orders
        + '}';
  }
}
