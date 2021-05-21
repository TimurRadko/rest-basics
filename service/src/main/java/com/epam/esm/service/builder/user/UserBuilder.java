package com.epam.esm.service.builder.user;

import com.epam.esm.dao.entity.Role;
import com.epam.esm.dao.entity.Users;
import com.epam.esm.service.dto.UsersCreatingDto;
import com.epam.esm.service.dto.UsersDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class UserBuilder {
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserBuilder(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  public Users build(UsersDto usersDto) {
    Users users = new Users();
    users.setId(usersDto.getId());
    users.setLogin(usersDto.getLogin());
    users.setBalance(usersDto.getBalance());
    return users;
  }

  public Users buildForSave(UsersCreatingDto usersCreatingDto) {
    Users users = new Users();
    users.setLogin(usersCreatingDto.getLogin());
    users.setPassword(passwordEncoder.encode(usersCreatingDto.getPassword()));
    users.setBalance(BigDecimal.valueOf(100));
    users.setRole(Role.USER);
    return users;
  }

  public Users buildFromGoogleParameters(String login) {
    Users users = new Users();
    users.setLogin(login);
    users.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
    users.setBalance(BigDecimal.valueOf(100));
    users.setRole(Role.USER);
    return users;
  }
}
