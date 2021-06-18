package com.epam.esm.service.exception.order;

import com.epam.esm.service.exception.ServiceException;

public class InsufficientFundInAccount extends ServiceException {
  public InsufficientFundInAccount(String message) {
    super(message);
  }
}
