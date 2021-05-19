package com.epam.esm.service.builder.user;

import com.epam.esm.dao.entity.Users;
import com.epam.esm.service.dto.UsersDto;
import org.springframework.stereotype.Component;

@Component
public class UserBuilder {

  public Users build(UsersDto usersDto) {
    Users users = new Users();
    users.setId(usersDto.getId());
    users.setLogin(usersDto.getLogin());
    users.setBalance(usersDto.getBalance());
    return users;
  }
}
