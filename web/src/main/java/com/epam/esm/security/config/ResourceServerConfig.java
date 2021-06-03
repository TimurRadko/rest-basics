package com.epam.esm.security.config;

import com.epam.esm.exception.handler.SingleExceptionResponse;
import com.epam.esm.service.locale.LocaleTranslator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.OutputStream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
  private static final int UNAUTHORIZED_CODE = 40101;
  private final LocaleTranslator localeTranslator;

  @Autowired
  public ResourceServerConfig(LocaleTranslator localeTranslator) {
    this.localeTranslator = localeTranslator;
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.GET, "/api/v2/certificates/**")
        .permitAll()
        .antMatchers(HttpMethod.POST, "/api/v2/users")
        .permitAll()
        .antMatchers(HttpMethod.POST, "/oauth/token")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(unauthorizedHandler());
  }

  @Bean
  public AuthenticationEntryPoint unauthorizedHandler() {
    return (request, response, exception) -> {
      SingleExceptionResponse exceptionResponse =
          prepareCustomExceptionResponse(
              localeTranslator.toLocale("exception.message.unauthorized"));
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.setContentType(APPLICATION_JSON_VALUE);
      OutputStream out = response.getOutputStream();
      ObjectMapper mapper = new ObjectMapper();
      mapper.writeValue(out, exceptionResponse);
      out.flush();
    };
  }

  private SingleExceptionResponse prepareCustomExceptionResponse(String message) {
    SingleExceptionResponse response = new SingleExceptionResponse();
    response.setErrorMessage(message);
    response.setErrorCode(ResourceServerConfig.UNAUTHORIZED_CODE);
    return response;
  }
}
