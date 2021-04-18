package com.epam.esm.service.dto;

import com.epam.esm.dao.entity.User;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class UserDto {
  private Long id;
  private String login;
  private BigDecimal account;
  private List<OrderDto> orderDtos;

  public UserDto() {}

  public UserDto(Long id, String login, BigDecimal account, List<OrderDto> orderDtos) {
    this.id = id;
    this.login = login;
    this.account = account;
    this.orderDtos = orderDtos;
  }

  public UserDto(User user, List<OrderDto> orderDtos) {
    this.id = user.getId();
    this.login = user.getLogin();
    this.account = user.getAccount();
    this.orderDtos = orderDtos;
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

  public List<OrderDto> getOrderDtos() {
    return Collections.unmodifiableList(orderDtos);
  }

  public void setOrderDtos(List<OrderDto> orderDtos) {
    this.orderDtos = orderDtos;
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
    return getOrderDtos() != null
        ? getOrderDtos().equals(userDto.getOrderDtos())
        : userDto.getOrderDtos() == null;
  }

  @Override
  public int hashCode() {
    int result = getId() != null ? getId().hashCode() : 0;
    result = 31 * result + (getLogin() != null ? getLogin().hashCode() : 0);
    result = 31 * result + (getAccount() != null ? getAccount().hashCode() : 0);
    result = 31 * result + (getOrderDtos() != null ? getOrderDtos().hashCode() : 0);
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
        + orderDtos
        + '}';
  }
}
