package com.epam.esm.web.exception.handler;

class SingleExceptionResponse {
  private String errorMessage;
  private int errorCode;

  SingleExceptionResponse() {}

  public String getErrorMessage() {
    return errorMessage;
  }

  void setMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public int getErrorCode() {
    return errorCode;
  }

  void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }
}
