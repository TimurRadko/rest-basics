package com.epam.esm.security.jwt;

import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

import javax.crypto.SecretKey;

@ConfigurationProperties(prefix = "security.jwt")
public class JwtConfig {
  private final String secretKey;
  private final String tokenPrefix;
  private final Integer tokenDaysExpirationPeriod;

  @ConstructorBinding
  public JwtConfig(String secretKey, String tokenPrefix, Integer tokenDaysExpirationPeriod) {
    this.secretKey = secretKey;
    this.tokenPrefix = tokenPrefix;
    this.tokenDaysExpirationPeriod = tokenDaysExpirationPeriod;
  }

  public String getTokenPrefix() {
    return tokenPrefix;
  }

  public Integer getTokenDaysExpirationPeriod() {
    return tokenDaysExpirationPeriod;
  }

  @Bean
  public SecretKey secretKey() {
    return Keys.hmacShaKeyFor(secretKey.getBytes());
  }

  public String getAuthorizationHeader() {
    return HttpHeaders.AUTHORIZATION;
  }
}
