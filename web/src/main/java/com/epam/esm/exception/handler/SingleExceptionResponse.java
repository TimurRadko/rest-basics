package com.epam.esm.exception.handler;

class SingleExceptionResponse extends AbstractExceptionResponse {
  private String errorMessage;

  SingleExceptionResponse() {}

  public String getErrorMessage() {
    return errorMessage;
  }

  void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}
