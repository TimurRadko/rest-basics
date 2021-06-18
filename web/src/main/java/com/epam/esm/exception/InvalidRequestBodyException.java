package com.epam.esm.exception;

public class InvalidRequestBodyException extends ControllerException {
  public InvalidRequestBodyException(String message) {
    super(message);
  }
}
