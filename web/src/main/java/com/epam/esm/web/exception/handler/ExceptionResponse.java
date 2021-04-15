package com.epam.esm.web.exception.handler;

class ExceptionResponse {
  private String errorMessage;
  private int errorCode;

  ExceptionResponse() {}

  ExceptionResponse(String errorMessage, int errorCode) {
    this.errorMessage = errorMessage;
    this.errorCode = errorCode;
  }

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
