package com.epam.esm.service.builder.user;

import com.epam.esm.dao.entity.User;
import com.epam.esm.service.builder.order.OrderDtoBuilder;
import com.epam.esm.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserDtoBuilder {
  private final OrderDtoBuilder orderDtoBuilder;

  @Autowired
  public UserDtoBuilder(OrderDtoBuilder orderDtoBuilder) {
    this.orderDtoBuilder = orderDtoBuilder;
  }

  public UserDto build(User user) {
    UserDto userDto = new UserDto();
    userDto.setId(user.getId());
    userDto.setLogin(user.getLogin());
    userDto.setAccount(user.getAccount());
    userDto.setOrders(
        user.getOrders().stream()
            .map(orderDtoBuilder::build)
            .collect(Collectors.toSet()));
    return userDto;
  }
}
