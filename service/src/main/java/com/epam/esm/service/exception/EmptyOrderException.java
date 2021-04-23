package com.epam.esm.service.exception;

public class EmptyOrderException extends ServiceException {
  public EmptyOrderException(String message) {
    super(message);
  }
}
