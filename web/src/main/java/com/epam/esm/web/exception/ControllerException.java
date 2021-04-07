package com.epam.esm.web.exception;

class ControllerException extends RuntimeException {
  ControllerException(String message) {
    super(message);
  }

  ControllerException(String message, Throwable cause) {
    super(message, cause);
  }

  ControllerException(Throwable cause) {
    super(cause);
  }

  ControllerException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
