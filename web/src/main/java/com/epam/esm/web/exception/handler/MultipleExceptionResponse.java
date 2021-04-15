package com.epam.esm.web.exception.handler;

import java.util.Collections;
import java.util.List;

class MultipleExceptionResponse extends AbstractExceptionResponse {
  private List<String> errorMessages;

  MultipleExceptionResponse() {}

  public List<String> getErrorMessages() {
    return Collections.unmodifiableList(errorMessages);
  }

  public void setErrorMessages(List<String> errorMessages) {
    this.errorMessages = errorMessages;
  }
}