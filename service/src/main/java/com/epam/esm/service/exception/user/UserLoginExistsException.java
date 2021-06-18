package com.epam.esm.service.exception.user;

import com.epam.esm.service.exception.ServiceException;

public class UserLoginExistsException extends ServiceException {
  public UserLoginExistsException(String message) {
    super(message);
  }
}
