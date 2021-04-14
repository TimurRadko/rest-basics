package com.epam.esm.web.exception;

public class EntityNotFoundException extends ControllerException {
  public EntityNotFoundException(String message) {
    super(message);
  }
}
