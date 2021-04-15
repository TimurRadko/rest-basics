package com.epam.esm.web.exception.handler;

class SingleExceptionResponse extends AbstractExceptionResponse {
  private String errorMessage;

  SingleExceptionResponse() {}

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}
