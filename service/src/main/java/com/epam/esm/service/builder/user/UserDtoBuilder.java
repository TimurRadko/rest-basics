package com.epam.esm.service.builder.user;

import com.epam.esm.dao.entity.Users;
import com.epam.esm.service.builder.order.OrdersDtoBuilder;
import com.epam.esm.service.dto.UsersDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserDtoBuilder {
  private final OrdersDtoBuilder ordersDtoBuilder;

  @Autowired
  public UserDtoBuilder(OrdersDtoBuilder ordersDtoBuilder) {
    this.ordersDtoBuilder = ordersDtoBuilder;
  }

  public UsersDto build(Users users) {
    UsersDto usersDto = new UsersDto();
    usersDto.setId(users.getId());
    usersDto.setLogin(users.getLogin());
    usersDto.setBalance(users.getBalance());
    usersDto.setOrders(
        users.getOrders().stream().map(ordersDtoBuilder::build).collect(Collectors.toSet()));
    return usersDto;
  }
}
