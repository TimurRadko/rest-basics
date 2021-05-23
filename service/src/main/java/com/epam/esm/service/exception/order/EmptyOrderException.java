package com.epam.esm.service.exception.order;

import com.epam.esm.service.exception.ServiceException;

public class EmptyOrderException extends ServiceException {
  public EmptyOrderException(String message) {
    super(message);
  }
}
