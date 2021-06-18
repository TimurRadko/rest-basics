package com.epam.esm.service.builder.user;

import com.epam.esm.dao.entity.Users;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserDetailsBuilder {

  public UserDetails build(Users user) {
    return new User(
        user.getLogin(),
        user.getPassword(),
        true,
        true,
        true,
        true,
        Set.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())));
  }
}
