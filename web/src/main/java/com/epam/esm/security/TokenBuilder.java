package com.epam.esm.security;

import com.epam.esm.security.jwt.JwtConfig;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
public class TokenBuilder {
  private final JwtConfig jwtConfig;

  @Autowired
  public TokenBuilder(JwtConfig jwtConfig) {
    this.jwtConfig = jwtConfig;
  }

  public String build(Authentication authResult) {
    return Jwts.builder()
        .setSubject(authResult.getName())
        .claim("authorities", authResult.getAuthorities())
        .setIssuedAt(new Date())
        .setExpiration(
            java.sql.Date.valueOf(
                LocalDate.now().plusDays(jwtConfig.getTokenDaysExpirationPeriod())))
        .signWith(jwtConfig.secretKey())
        .compact();
  }

  public String buildWithUserLogin(Authentication authResult, UserDetails user) {
    return Jwts.builder()
        .setSubject(user.getUsername())
        .claim("authorities", authResult.getAuthorities())
        .setIssuedAt(new Date())
        .setExpiration(
            java.sql.Date.valueOf(
                LocalDate.now().plusDays(jwtConfig.getTokenDaysExpirationPeriod())))
        .signWith(jwtConfig.secretKey())
        .compact();
  }
}
