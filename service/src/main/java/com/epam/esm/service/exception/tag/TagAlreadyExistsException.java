package com.epam.esm.service.exception.tag;

import com.epam.esm.service.exception.ServiceException;

public class TagAlreadyExistsException extends ServiceException {
  public TagAlreadyExistsException(String message) {
    super(message);
  }
}
