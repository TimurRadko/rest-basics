package com.epam.esm.service.exception.user;

import com.epam.esm.service.exception.ServiceException;

public class UserLoginNotFoundException extends ServiceException {
  public UserLoginNotFoundException(String message) {
    super(message);
  }
}
