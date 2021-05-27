package com.epam.esm.exception.handler;

public class SingleExceptionResponse extends AbstractExceptionResponse {
  private String errorMessage;

  public SingleExceptionResponse() {}

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}
