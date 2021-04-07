package com.epam.esm.web.exception.handler;

import com.epam.esm.web.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  private static final Integer ERROR_40401 = 40401;
  private static final Integer ERROR_40400 = 40400;

  @ExceptionHandler
  public ResponseEntity<GiftCertificateIncorrectData> handleException(
      EntityNotFoundException exception) {
    GiftCertificateIncorrectData data = new GiftCertificateIncorrectData();
    data.setMessage(exception.getMessage());
    data.setErrorCode(ERROR_40401);
    return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler
  public ResponseEntity<GiftCertificateIncorrectData> handleException(Exception exception) {
    GiftCertificateIncorrectData data = new GiftCertificateIncorrectData();
    data.setMessage(exception.getMessage());
    data.setErrorCode(ERROR_40400);
    return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
  }
}
