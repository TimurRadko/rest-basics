package com.epam.esm.security;

import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.UsersDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserSecurity {
  private final UserService userService;

  @Autowired
  public UserSecurity(UserService userService) {
    this.userService = userService;
  }

  public boolean hasSameName(Authentication authentication, Long id) {
    Optional<UsersDto> optionalUsersDto = userService.getById(id);
    String login = authentication.getName();
    if (optionalUsersDto.isPresent()) {
      String existingUserLogin = optionalUsersDto.get().getLogin();
      return existingUserLogin.equals(login);
    }
    return false;
  }
}
