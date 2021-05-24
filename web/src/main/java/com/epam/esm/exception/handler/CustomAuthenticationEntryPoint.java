package com.epam.esm.exception.handler;

import com.epam.esm.service.locale.TranslatorLocale;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
  private static final int UNAUTHORIZED_CODE = 40101;
  private final TranslatorLocale translatorLocale;

  @Autowired
  public CustomAuthenticationEntryPoint(TranslatorLocale translatorLocale) {
    this.translatorLocale = translatorLocale;
  }

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException {
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
