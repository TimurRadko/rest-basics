package com.epam.esm.security.jwt;

import com.epam.esm.exception.handler.SingleExceptionResponse;
import com.epam.esm.security.LoginAndPasswordAuthenticationRequest;
import com.epam.esm.security.TokenBuilder;
import com.epam.esm.service.locale.TranslatorLocale;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
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
import java.io.OutputStream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class JwtLoginAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private static final int UNAUTHORIZED_CODE = 40101;
  private final AuthenticationManager authenticationManager;
  private final JwtConfig jwtConfig;
  private final TokenBuilder tokenBuilder;
  private final TranslatorLocale translatorLocale;

  public JwtLoginAndPasswordAuthenticationFilter(
      AuthenticationManager authenticationManager,
      JwtConfig jwtConfig,
      TokenBuilder tokenBuilder,
      TranslatorLocale translatorLocale) {
    this.authenticationManager = authenticationManager;
    this.jwtConfig = jwtConfig;
    this.tokenBuilder = tokenBuilder;
    this.translatorLocale = translatorLocale;
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
    String token = tokenBuilder.build(authResult);
    response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + token);
  }

  @Override
  protected void unsuccessfulAuthentication(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
      throws IOException, ServletException {
    SingleExceptionResponse exceptionResponse = new SingleExceptionResponse();
    exceptionResponse.setErrorCode(UNAUTHORIZED_CODE);
    exceptionResponse.setErrorMessage(translatorLocale.toLocale("exception.message.unauthorized"));
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType(APPLICATION_JSON_VALUE);
    OutputStream out = response.getOutputStream();
    ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(out, exceptionResponse);
    out.flush();
  }
}
