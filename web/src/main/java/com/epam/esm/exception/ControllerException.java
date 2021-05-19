package com.epam.esm.exception;

class ControllerException extends RuntimeException {
  ControllerException(String message) {
    super(message);
  }
}
