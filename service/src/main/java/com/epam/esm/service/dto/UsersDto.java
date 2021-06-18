package com.epam.esm.service.dto;

import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class UsersDto extends RepresentationModel<UsersDto> {
  private Long id;
  private String login;
  private BigDecimal balance;
  private Set<OrdersDto> orders;

  public UsersDto() {}

  public UsersDto(Long id, String login, BigDecimal balance, Set<OrdersDto> orders) {
    this.id = id;
    this.login = login;
    this.balance = balance;
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

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public Set<OrdersDto> getOrders() {
    return (orders == null) ? null : new HashSet<>(orders);
  }

  public void setOrders(Set<OrdersDto> orders) {
    this.orders = orders;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UsersDto)) {
      return false;
    }

    UsersDto usersDto = (UsersDto) o;

    if (getId() != null ? !getId().equals(usersDto.getId()) : usersDto.getId() != null) {
      return false;
    }
    if (getLogin() != null ? !getLogin().equals(usersDto.getLogin()) : usersDto.getLogin() != null) {
      return false;
    }
    if (getBalance() != null
        ? !getBalance().equals(usersDto.getBalance())
        : usersDto.getBalance() != null) {
      return false;
    }
    return getOrders() != null
        ? getOrders().equals(usersDto.getOrders())
        : usersDto.getOrders() == null;
  }

  @Override
  public int hashCode() {
    int result = getId() != null ? getId().hashCode() : 0;
    result = 31 * result + (getLogin() != null ? getLogin().hashCode() : 0);
    result = 31 * result + (getBalance() != null ? getBalance().hashCode() : 0);
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
        + ", balance="
        + balance
        + ", orderDtos="
        + orders
        + '}';
  }
}
