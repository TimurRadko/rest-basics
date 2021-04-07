package com.epam.esm.web.exception;

public class TagAlreadyExistsException extends ControllerException {
  public TagAlreadyExistsException(String message) {
    super(message);
  }

  public TagAlreadyExistsException(String message, Throwable cause) {
    super(message, cause);
  }

  public TagAlreadyExistsException(Throwable cause) {
    super(cause);
  }

  public TagAlreadyExistsException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
