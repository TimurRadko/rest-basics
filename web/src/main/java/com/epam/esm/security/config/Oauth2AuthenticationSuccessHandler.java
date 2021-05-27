package com.epam.esm.security.config;

import com.epam.esm.dao.entity.Users;
import com.epam.esm.security.TokenBuilder;
import com.epam.esm.security.jwt.JwtConfig;
import com.epam.esm.service.UserService;
import com.epam.esm.service.builder.user.UserBuilder;
import com.epam.esm.service.builder.user.UserDetailsBuilder;
import com.epam.esm.service.dto.UsersCreatingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Component
public class Oauth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  private final UserService userService;
  private final JwtConfig jwtConfig;
  private final UserBuilder userBuilder;
  private final UserDetailsBuilder userDetailsBuilder;
  private final TokenBuilder tokenBuilder;

  @Autowired
  public Oauth2AuthenticationSuccessHandler(
      UserService userService,
      JwtConfig jwtConfig,
      UserBuilder userBuilder,
      UserDetailsBuilder userDetailsBuilder,
      TokenBuilder tokenBuilder) {
    this.userService = userService;
    this.jwtConfig = jwtConfig;
    this.userBuilder = userBuilder;
    this.userDetailsBuilder = userDetailsBuilder;
    this.tokenBuilder = tokenBuilder;
  }

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException {
    DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();
    Map<String, Object> claims = principal.getClaims();
    String name = (String) claims.get("name");

    UserDetails user;
    try {
      user = userService.loadUserByUsername(name);
    } catch (UsernameNotFoundException e) {
      Users builtUser = userBuilder.buildFromGoogleParameters(name);
      user = userDetailsBuilder.build(builtUser);
      userService.save(
          new UsersCreatingDto(
              builtUser.getLogin(), builtUser.getPassword(), builtUser.getPassword()));
    }
    String token = tokenBuilder.buildWithUserLogin(authentication, user);
    PrintWriter writer = response.getWriter();
    writer.write(jwtConfig.getTokenPrefix() + token);
  }
}
