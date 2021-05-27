package com.epam.esm.security.config;

import com.epam.esm.security.TokenBuilder;
import com.epam.esm.security.jwt.JwtConfig;
import com.epam.esm.security.jwt.JwtLoginAndPasswordAuthenticationFilter;
import com.epam.esm.security.jwt.JwtTokenVerifierFilter;
import com.epam.esm.service.UserService;
import com.epam.esm.service.locale.TranslatorLocale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@ComponentScan("com.epam.esm")
@PropertySource("classpath:application.properties")
@EnableConfigurationProperties(JwtConfig.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final PasswordEncoder passwordEncoder;
  private final UserService userService;
  private final JwtConfig jwtConfig;
  private final Oauth2AuthenticationSuccessHandler authenticationSuccessHandler;
  private final TokenBuilder tokenBuilder;
  private final TranslatorLocale translatorLocale;

  @Autowired
  public SecurityConfig(
      PasswordEncoder passwordEncoder,
      UserService userService,
      JwtConfig jwtConfig,
      Oauth2AuthenticationSuccessHandler authenticationSuccessHandler,
      TokenBuilder tokenBuilder,
      TranslatorLocale translatorLocale) {
    this.passwordEncoder = passwordEncoder;
    this.userService = userService;
    this.jwtConfig = jwtConfig;
    this.authenticationSuccessHandler = authenticationSuccessHandler;
    this.tokenBuilder = tokenBuilder;
    this.translatorLocale = translatorLocale;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilter(
            new JwtLoginAndPasswordAuthenticationFilter(
                authenticationManager(), jwtConfig, tokenBuilder, translatorLocale))
        .addFilterAfter(
            new JwtTokenVerifierFilter(jwtConfig), JwtLoginAndPasswordAuthenticationFilter.class)
        .authorizeRequests()
        .antMatchers(HttpMethod.GET, "/api/v2/certificates/**")
        .permitAll()
        .antMatchers(HttpMethod.POST, "/api/v2/users")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .oauth2Login()
        .successHandler(authenticationSuccessHandler);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(daoAuthenticationProvider());
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder);
    provider.setUserDetailsService(userService);
    return provider;
  }
}
