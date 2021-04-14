package com.epam.esm.web.exception.handler;

class SingleExceptionResponse {
  private String errorMessage;
  private Integer errorCode;

  SingleExceptionResponse() {}

  public String getErrorMessage() {
    return errorMessage;
  }

  void setMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public Integer getErrorCode() {
    return errorCode;
  }

  void setErrorCode(Integer errorCode) {
    this.errorCode = errorCode;
  }
}
