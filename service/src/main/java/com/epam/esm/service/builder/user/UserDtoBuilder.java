package com.epam.esm.service.builder.user;

import com.epam.esm.dao.entity.User;
import com.epam.esm.service.builder.order.OrdersDtoBuilder;
import com.epam.esm.service.dto.UserDto;
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

  public UserDto build(User user) {
    UserDto userDto = new UserDto();
    userDto.setId(user.getId());
    userDto.setLogin(user.getLogin());
    userDto.setAccount(user.getAccount());
    userDto.setOrders(
        user.getOrders().stream()
            .map(ordersDtoBuilder::build)
            .collect(Collectors.toSet()));
    return userDto;
  }
}
