package com.epam.esm.web.exception.handler;

import com.epam.esm.service.exception.DeletingTagException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.EntityNotValidException;
import com.epam.esm.service.exception.EntityNotValidMultipleException;
import com.epam.esm.service.exception.TagAlreadyExistsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
  private static final int ERROR_40401 = 40401;
  private static final int ERROR_40402 = 40402;
  private static final int ERROR_41501 = 41501;
  private static final int ERROR_40901 = 40901;
  private static final int ERROR_40001 = 40001;
  private static final int ERROR_40002 = 40002;
  private static final int ERROR_40003 = 40003;
  private static final int ERROR_40004 = 40004;

  @ExceptionHandler
  public ResponseEntity<SingleExceptionResponse> handleException(
      EntityNotFoundException exception) {
    SingleExceptionResponse response = new SingleExceptionResponse();
    response.setErrorMessage(exception.getMessage());
    response.setErrorCode(ERROR_40401);
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler
  public ResponseEntity<SingleExceptionResponse> handleException(
      TagAlreadyExistsException exception) {
    SingleExceptionResponse response = new SingleExceptionResponse();
    response.setErrorMessage(exception.getMessage());
    response.setErrorCode(ERROR_40901);
    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
  }

  @ExceptionHandler
  public ResponseEntity<SingleExceptionResponse> handleException(DeletingTagException exception) {
    SingleExceptionResponse response = new SingleExceptionResponse();
    response.setErrorMessage(exception.getMessage());
    response.setErrorCode(ERROR_40002);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<MultipleExceptionResponse> handleException(
      EntityNotValidMultipleException exception) {
    MultipleExceptionResponse response = new MultipleExceptionResponse();
    List<String> exceptionList = new ArrayList<>(Arrays.asList(exception.getMessage().split("\n")));
    response.setErrorMessages(exceptionList);
    response.setErrorCode(ERROR_40003);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<SingleExceptionResponse> handleException(EntityNotValidException exception) {
    SingleExceptionResponse response = new SingleExceptionResponse();
    response.setErrorMessage(exception.getMessage());
    response.setErrorCode(ERROR_40004);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      HttpMediaTypeNotSupportedException exception,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    SingleExceptionResponse response = new SingleExceptionResponse();
    response.setErrorMessage(exception.getMessage());
    response.setErrorCode(ERROR_41501);
    return new ResponseEntity<>(response, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
  }

  @ExceptionHandler
  public ResponseEntity<SingleExceptionResponse> handleException(Exception exception) {
    SingleExceptionResponse response = new SingleExceptionResponse();
    response.setErrorMessage(exception.getMessage());
    response.setErrorCode(ERROR_40001);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      NoHandlerFoundException exception,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    SingleExceptionResponse response = new SingleExceptionResponse();
    response.setErrorMessage(exception.getMessage());
    response.setErrorCode(ERROR_40402);
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }
}
