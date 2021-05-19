package com.epam.esm.security.jwt;

import com.epam.esm.security.LoginAndPasswordAuthenticationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class JwtLoginAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private final AuthenticationManager authenticationManager;
  private final JwtConfig jwtConfig;

  public JwtLoginAndPasswordAuthenticationFilter(
      AuthenticationManager authenticationManager, JwtConfig jwtConfig) {
    this.authenticationManager = authenticationManager;
    this.jwtConfig = jwtConfig;
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    try {
      LoginAndPasswordAuthenticationRequest authenticationRequest =
          new ObjectMapper()
              .readValue(request.getInputStream(), LoginAndPasswordAuthenticationRequest.class);
      String login = authenticationRequest.getLogin();
      String password = authenticationRequest.getPassword();
      Authentication authentication = new UsernamePasswordAuthenticationToken(login, password);
      return authenticationManager.authenticate(authentication);
    } catch (IOException e) {
      throw new SecurityException(e);
    }
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult)
      throws IOException, ServletException {

    String token =
        Jwts.builder()
            .setSubject(authResult.getName())
            .claim("authorities", authResult.getAuthorities())
            .setIssuedAt(new Date())
            .setExpiration(
                java.sql.Date.valueOf(
                    LocalDate.now().plusDays(jwtConfig.getTokenDaysExpirationPeriod())))
            .signWith(jwtConfig.secretKey())
            .compact();

    response.addHeader(jwtConfig.authorizationHeader(), jwtConfig.getTokenPrefix() + token);
  }
}
