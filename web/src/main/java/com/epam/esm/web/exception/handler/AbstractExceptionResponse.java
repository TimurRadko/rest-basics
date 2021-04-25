package com.epam.esm.web.exception.handler;

public abstract class AbstractExceptionResponse {
  private int errorCode;

  AbstractExceptionResponse() {}

  public int getErrorCode() {
    return errorCode;
  }

  void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }
}
