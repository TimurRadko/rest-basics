package com.epam.esm.web.exception.handler;

import com.epam.esm.service.exception.EntityNotValidException;
import com.epam.esm.web.exception.EntityNotFoundException;
import com.epam.esm.web.exception.InvalidRequestBodyException;
import com.epam.esm.web.exception.TagAlreadyExistsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
  private static final int ERROR_40401 = 40401;
  private static final int ERROR_41501 = 41501;
  private static final int ERROR_40901 = 40901;
  private static final int ERROR_40001 = 40001;
  private static final int ERROR_40400 = 40400;

  @ExceptionHandler
  public ResponseEntity<SingleExceptionResponse> handleException(
      EntityNotFoundException exception) {
    SingleExceptionResponse data = new SingleExceptionResponse();
    data.setMessage(exception.getMessage());
    data.setErrorCode(ERROR_40401);
    return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler
  public ResponseEntity<SingleExceptionResponse> handleException(
      TagAlreadyExistsException exception) {
    SingleExceptionResponse data = new SingleExceptionResponse();
    data.setMessage(exception.getMessage());
    data.setErrorCode(ERROR_40901);
    return new ResponseEntity<>(data, HttpStatus.CONFLICT);
  }

  @ExceptionHandler
  public ResponseEntity<SingleExceptionResponse> handleException(
      EntityNotValidException exception) {
    SingleExceptionResponse data = new SingleExceptionResponse();
    data.setMessage(exception.getMessage());
    data.setErrorCode(ERROR_40001);
    return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      HttpMediaTypeNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    SingleExceptionResponse data = new SingleExceptionResponse();
    data.setMessage(ex.getMessage());
    data.setErrorCode(ERROR_41501);
    return new ResponseEntity<>(data, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
  }

  //  @ExceptionHandler
  //  public ResponseEntity<SingleExceptionResponse> handleException(Exception exception) {
  //    SingleExceptionResponse data = new SingleExceptionResponse();
  //    data.setMessage(exception.getMessage());
  //    data.setErrorCode(ERROR_40400);
  //    return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
  //  }
}
