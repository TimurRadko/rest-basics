package com.epam.esm.security.config;

import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
@Import(SecurityConfig.class)
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
  private static final String USERNAME = "user";
  private static final String PASSWORD = "user";
  private static final String GRANT_TYPE_PASSWORD = "password";
  private static final String AUTHORIZATION_CODE = "authorization_code";
  private static final String REFRESH_TOKEN = "refresh_token";
  private static final String IMPLICIT = "implicit";
  private static final String SCOPE_READ = "read";
  private static final String SCOPE_WRITE = "write";
  private static final String TRUST = "trust";
  private static final int TWELVE_HOURS = 60 * 60 * 12;
  private static final int THIRTY_DAYS = 60 * 60 * 24 * 30;

  private final TokenStore tokenStore;
  private final JwtAccessTokenConverter jwtTokenEnhancer;
  private final UserApprovalHandler userApprovalHandler;
  private final AuthenticationManager authenticationManager;
  private final UserService userService;
  private final PasswordConfig passwordConfig;

  @Autowired
  public AuthorizationServerConfig(
      TokenStore tokenStore,
      JwtAccessTokenConverter jwtTokenEnhancer,
      UserApprovalHandler userApprovalHandler,
      @Qualifier("authenticationManagerBean") AuthenticationManager authenticationManager,
      @Qualifier("userServiceImpl") UserService userService,
      PasswordConfig passwordConfig) {
    this.tokenStore = tokenStore;
    this.jwtTokenEnhancer = jwtTokenEnhancer;
    this.userApprovalHandler = userApprovalHandler;
    this.authenticationManager = authenticationManager;
    this.userService = userService;
    this.passwordConfig = passwordConfig;
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients
        .inMemory()
        .withClient(USERNAME)
        .secret(passwordConfig.passwordEncoder().encode(PASSWORD))
        .authorizedGrantTypes(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT)
        .scopes(SCOPE_READ, SCOPE_WRITE, TRUST)
        .accessTokenValiditySeconds(TWELVE_HOURS)
        .refreshTokenValiditySeconds(THIRTY_DAYS);
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints
        .tokenStore(tokenStore)
        .tokenEnhancer(jwtTokenEnhancer)
        .userApprovalHandler(userApprovalHandler)
        .authenticationManager(authenticationManager)
        .userDetailsService(userService);
  }
}
