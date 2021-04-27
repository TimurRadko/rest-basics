package com.epam.esm.service.builder.user;

import com.epam.esm.dao.entity.User;
import com.epam.esm.service.builder.order.OrdersBuilder;
import com.epam.esm.service.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserBuilder {
  private final OrdersBuilder ordersBuilder;

  public UserBuilder(OrdersBuilder ordersBuilder) {
    this.ordersBuilder = ordersBuilder;
  }

  public User build(UserDto userDto) {
    User user = new User();
    user.setId(userDto.getId());
    user.setLogin(userDto.getLogin());
    user.setAccount(userDto.getAccount());
    return user;
  }
}
