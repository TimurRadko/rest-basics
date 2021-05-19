package com.epam.esm.security.config;

import com.epam.esm.dao.entity.Role;
import com.epam.esm.security.jwt.JwtConfig;
import com.epam.esm.security.jwt.JwtLoginAndPasswordAuthenticationFilter;
import com.epam.esm.security.jwt.JwtTokenVerifierFilter;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@ComponentScan("com.epam.esm")
@PropertySource("classpath:application.properties")
@EnableConfigurationProperties(JwtConfig.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final UserService userService;
  private final JwtConfig jwtConfig;

  @Autowired
  public SecurityConfig(UserService userService, JwtConfig jwtConfig) {
    this.userService = userService;
    this.jwtConfig = jwtConfig;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilter(new JwtLoginAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig))
        .addFilterAfter(
            new JwtTokenVerifierFilter(jwtConfig), JwtLoginAndPasswordAuthenticationFilter.class)
        .authorizeRequests()
        .antMatchers("/")
        .permitAll()
        .antMatchers("/api/**")
        .hasAnyRole(Role.GUEST.name(), Role.USER.name(), Role.ADMIN.name())
        .anyRequest()
        .authenticated();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(daoAuthenticationProvider());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10);
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder());
    provider.setUserDetailsService(userService);
    return provider;
  }
}
