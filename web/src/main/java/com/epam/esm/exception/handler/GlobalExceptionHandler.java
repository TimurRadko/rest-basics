package com.epam.esm.exception.handler;

import com.epam.esm.service.exception.certificates.DeletingGiftCertificateException;
import com.epam.esm.service.exception.tag.DeletingTagException;
import com.epam.esm.service.exception.EmptyOrderException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.EntityNotValidException;
import com.epam.esm.service.exception.EntityNotValidMultipleException;
import com.epam.esm.service.exception.order.InsufficientFundInAccount;
import com.epam.esm.service.exception.PageNotValidException;
import com.epam.esm.service.exception.tag.TagAlreadyExistsException;
import com.epam.esm.service.exception.user.UserDoesNotHaveOrderException;
import org.springframework.beans.TypeMismatchException;
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
  private static final int ENTITY_NOT_FOUND_CODE = 40401;
  private static final int NO_HANDLER_FOUND_CODE = 40402;
  private static final int HTTP_MEDIA_TYPE_NOT_SUPPORTED_CODE = 41501;
  private static final int TAG_ALREADY_EXIST_CODE = 40901;
  private static final int COMMON_CODE = 40001;
  private static final int DELETING_TAG_CODE = 40002;
  private static final int ENTITY_NOT_VALID_MULTIPLE_FIELDS_CODE = 40003;
  private static final int ENTITY_NOT_VALID_SINGLE_FIELD_CODE = 40004;
  private static final int INSUFFICIENT_FUND_IN_ACCOUNT_CODE = 40005;
  private static final int EMPTY_ORDER_CODE = 40006;
  private static final int PAGE_NOT_VALID_CODE = 40007;
  private static final int PAGE_OR_SIZE_PASS_TYPE_NOT_VALID_CODE = 40008;
  private static final int USER_DOES_NOT_HAVE_ORDER_CODE = 40009;
  private static final int DELETING_CERTIFICATE_CODE = 40010;

  @ExceptionHandler
  public ResponseEntity<SingleExceptionResponse> handleException(
      EntityNotFoundException exception) {
    SingleExceptionResponse response = new SingleExceptionResponse();
    response.setErrorMessage(exception.getMessage());
    response.setErrorCode(ENTITY_NOT_FOUND_CODE);
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler
  public ResponseEntity<SingleExceptionResponse> handleException(
      TagAlreadyExistsException exception) {
    SingleExceptionResponse response = new SingleExceptionResponse();
    response.setErrorMessage(exception.getMessage());
    response.setErrorCode(TAG_ALREADY_EXIST_CODE);
    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
  }

  @ExceptionHandler
  public ResponseEntity<SingleExceptionResponse> handleException(DeletingTagException exception) {
    SingleExceptionResponse response = new SingleExceptionResponse();
    response.setErrorMessage(exception.getMessage());
    response.setErrorCode(DELETING_TAG_CODE);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<MultipleExceptionResponse> handleException(
      EntityNotValidMultipleException exception) {
    MultipleExceptionResponse response = new MultipleExceptionResponse();
    List<String> exceptionList = new ArrayList<>(Arrays.asList(exception.getMessage().split("\n")));
    response.setErrorMessages(exceptionList);
    response.setErrorCode(ENTITY_NOT_VALID_MULTIPLE_FIELDS_CODE);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<SingleExceptionResponse> handleException(
      EntityNotValidException exception) {
    SingleExceptionResponse response = new SingleExceptionResponse();
    response.setErrorMessage(exception.getMessage());
    response.setErrorCode(ENTITY_NOT_VALID_SINGLE_FIELD_CODE);
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
    response.setErrorCode(HTTP_MEDIA_TYPE_NOT_SUPPORTED_CODE);
    return new ResponseEntity<>(response, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
  }

  @Override
  protected ResponseEntity<Object> handleTypeMismatch(
      TypeMismatchException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
    SingleExceptionResponse response = new SingleExceptionResponse();
    response.setErrorMessage("The passed parameter must be number");
    response.setErrorCode(PAGE_OR_SIZE_PASS_TYPE_NOT_VALID_CODE);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<SingleExceptionResponse> handleException(Exception exception) {
    SingleExceptionResponse response = new SingleExceptionResponse();
    response.setErrorMessage(exception.getMessage());
    response.setErrorCode(COMMON_CODE);
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
    response.setErrorCode(NO_HANDLER_FOUND_CODE);
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler
  public ResponseEntity<SingleExceptionResponse> handleException(
      InsufficientFundInAccount exception) {
    SingleExceptionResponse response = new SingleExceptionResponse();
    response.setErrorMessage(exception.getMessage());
    response.setErrorCode(INSUFFICIENT_FUND_IN_ACCOUNT_CODE);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<SingleExceptionResponse> handleException(EmptyOrderException exception) {
    SingleExceptionResponse response = new SingleExceptionResponse();
    response.setErrorMessage(exception.getMessage());
    response.setErrorCode(EMPTY_ORDER_CODE);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<SingleExceptionResponse> handleException(
      UserDoesNotHaveOrderException exception) {
    SingleExceptionResponse response = new SingleExceptionResponse();
    response.setErrorMessage(exception.getMessage());
    response.setErrorCode(USER_DOES_NOT_HAVE_ORDER_CODE);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<MultipleExceptionResponse> handleException(
      PageNotValidException exception) {
    MultipleExceptionResponse response = new MultipleExceptionResponse();
    List<String> exceptionList = new ArrayList<>(Arrays.asList(exception.getMessage().split("\n")));
    response.setErrorMessages(exceptionList);
    response.setErrorCode(PAGE_NOT_VALID_CODE);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<SingleExceptionResponse> handleException(
      DeletingGiftCertificateException exception) {
    SingleExceptionResponse response = new SingleExceptionResponse();
    response.setErrorMessage(exception.getMessage());
    response.setErrorCode(DELETING_CERTIFICATE_CODE);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }
}
