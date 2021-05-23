package com.epam.esm.service.exception.user;

import com.epam.esm.service.exception.ServiceException;

public class UserLoginExistingException extends ServiceException {
  public UserLoginExistingException(String message) {
    super(message);
  }
}
