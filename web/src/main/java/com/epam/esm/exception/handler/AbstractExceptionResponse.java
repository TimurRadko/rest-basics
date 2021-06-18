package com.epam.esm.exception.handler;

public abstract class AbstractExceptionResponse {
  private int errorCode;

  AbstractExceptionResponse() {}

  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }
}
