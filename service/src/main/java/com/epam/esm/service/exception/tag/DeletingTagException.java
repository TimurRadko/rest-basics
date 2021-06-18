package com.epam.esm.service.exception.tag;

import com.epam.esm.service.exception.ServiceException;

public class DeletingTagException extends ServiceException {
  public DeletingTagException(String message) {
    super(message);
  }
}
