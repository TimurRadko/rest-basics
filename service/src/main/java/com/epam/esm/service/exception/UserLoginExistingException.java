package com.epam.esm.service.exception;

public class UserLoginExistingException extends ServiceException {
  public UserLoginExistingException(String message) {
    super(message);
  }
}
