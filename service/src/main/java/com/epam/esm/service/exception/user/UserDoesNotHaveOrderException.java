package com.epam.esm.service.exception.user;

import com.epam.esm.service.exception.ServiceException;

public class UserDoesNotHaveOrderException extends ServiceException {
  public UserDoesNotHaveOrderException(String message) {
    super(message);
  }
}
