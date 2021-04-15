package com.epam.esm.service.exception;

public class EntityNotFoundException extends ServiceException {
  public EntityNotFoundException(String message) {
    super(message);
  }
}
