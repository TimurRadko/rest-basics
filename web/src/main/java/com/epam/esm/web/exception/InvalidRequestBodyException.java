package com.epam.esm.web.exception;

public class InvalidRequestBodyException extends ControllerException {
  public InvalidRequestBodyException(String message) {
    super(message);
  }
}
