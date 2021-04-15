package com.epam.esm.web.exception.handler;

import com.epam.esm.service.exception.DeletingTagException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.EntityNotValidException;
import com.epam.esm.service.exception.TagAlreadyExistsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
  private static final int ERROR_40401 = 40401;
  private static final int ERROR_41501 = 41501;
  private static final int ERROR_40901 = 40901;
  private static final int ERROR_40001 = 40001;

  @ExceptionHandler
  public ResponseEntity<ExceptionResponse> handleException(EntityNotFoundException exception) {
    ExceptionResponse data = new ExceptionResponse();
    data.setMessage(exception.getMessage());
    data.setErrorCode(ERROR_40401);
    return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler
  public ResponseEntity<ExceptionResponse> handleException(TagAlreadyExistsException exception) {
    ExceptionResponse data = new ExceptionResponse();
    data.setMessage(exception.getMessage());
    data.setErrorCode(ERROR_40901);
    return new ResponseEntity<>(data, HttpStatus.CONFLICT);
  }

  @ExceptionHandler
  public ResponseEntity<ExceptionResponse> handleException(DeletingTagException exception) {
    ExceptionResponse data = new ExceptionResponse();
    data.setMessage(exception.getMessage());
    data.setErrorCode(ERROR_40001);
    return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<List<ExceptionResponse>> handleException(
      EntityNotValidException exception) {
    List<ExceptionResponse> exceptionList = new ArrayList<>();
    String[] exceptions = exception.getMessage().split("\n");
    for (String ex : exceptions) {
      exceptionList.add(new ExceptionResponse(ex, ERROR_40401));
    }
    return new ResponseEntity<>(exceptionList, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      HttpMediaTypeNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    ExceptionResponse data = new ExceptionResponse();
    data.setMessage(ex.getMessage());
    data.setErrorCode(ERROR_41501);
    return new ResponseEntity<>(data, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
  }

  @ExceptionHandler
  public ResponseEntity<ExceptionResponse> handleException(Exception exception) {
    ExceptionResponse data = new ExceptionResponse();
    data.setMessage(exception.getMessage());
    data.setErrorCode(ERROR_40001);
    return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
  }
}
