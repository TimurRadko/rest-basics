package com.epam.esm.service.builder.user;

import com.epam.esm.dao.entity.User;
import com.epam.esm.service.builder.order.OrderBuilder;
import com.epam.esm.service.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserBuilder {
  private final OrderBuilder orderBuilder;

  public UserBuilder(OrderBuilder orderBuilder) {
    this.orderBuilder = orderBuilder;
  }

  public User build(UserDto userDto) {
    User user = new User();
    user.setId(userDto.getId());
    user.setLogin(userDto.getLogin());
    user.setAccount(userDto.getAccount());
    return user;
  }
}
