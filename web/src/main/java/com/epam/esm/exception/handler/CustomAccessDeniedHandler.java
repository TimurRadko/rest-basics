package com.epam.esm.exception.handler;

import com.epam.esm.service.locale.TranslatorLocale;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.StringJoiner;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
  private static final int ACCESS_DENIED_CODE = 40301;
  private final TranslatorLocale translatorLocale;

  @Autowired
  public CustomAccessDeniedHandler(TranslatorLocale translatorLocale) {
    this.translatorLocale = translatorLocale;
  }

  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException {
    SingleExceptionResponse exceptionResponse = new SingleExceptionResponse();
    exceptionResponse.setErrorCode(ACCESS_DENIED_CODE);
    exceptionResponse.setErrorMessage(translatorLocale.toLocale("exception.message.accessDenied"));
    response.setStatus(HttpStatus.FORBIDDEN.value());
    response.setContentType(APPLICATION_JSON_VALUE);
    OutputStream out = response.getOutputStream();
    ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(out, exceptionResponse);
    out.flush();
  }
}
