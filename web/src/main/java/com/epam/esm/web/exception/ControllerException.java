package com.epam.esm.web.exception;

class ControllerException extends RuntimeException {
  ControllerException(String message) {
    super(message);
  }
}
