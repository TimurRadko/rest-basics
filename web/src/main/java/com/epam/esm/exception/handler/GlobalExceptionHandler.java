package com.epam.esm.exception.handler;

import com.epam.esm.service.exception.EntityNotSavedException;
import com.epam.esm.service.exception.order.EmptyOrderException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.EntityNotValidException;
import com.epam.esm.service.exception.EntityNotValidMultipleException;
import com.epam.esm.service.exception.PageNotValidException;
import com.epam.esm.service.exception.certificates.DeletingGiftCertificateException;
import com.epam.esm.service.exception.order.InsufficientFundInAccount;
import com.epam.esm.service.exception.tag.DeletingTagException;
import com.epam.esm.service.exception.tag.TagAlreadyExistsException;
import com.epam.esm.service.exception.user.UserDoesNotHaveOrderException;
import com.epam.esm.service.exception.user.UserLoginExistsException;
import com.epam.esm.service.exception.user.UserLoginNotFoundException;
import com.epam.esm.service.locale.TranslatorLocale;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
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
  private final TranslatorLocale translatorLocale;
  private static final int ENTITY_NOT_FOUND_CODE = 40401;
  private static final int NO_HANDLER_FOUND_CODE = 40402;
  private static final int HTTP_MEDIA_TYPE_NOT_SUPPORTED_CODE = 41501;
  private static final int TAG_ALREADY_EXIST_CODE = 40901;
  private static final int USER_ALREADY_EXIST_CODE = 40902;
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
  private static final int ENTITY_NOT_SAVED_CODE = 40011;
  private static final int USER_LOGIN_NOT_FOUND_CODE = 40012;

  @Autowired
  public GlobalExceptionHandler(TranslatorLocale translatorLocale) {
    this.translatorLocale = translatorLocale;
  }

  @ExceptionHandler
  public ResponseEntity<SingleExceptionResponse> handleException(
      EntityNotFoundException exception) {
    SingleExceptionResponse response =
        prepareSingleExceptionResponse(ENTITY_NOT_FOUND_CODE, exception);
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler
  public ResponseEntity<SingleExceptionResponse> handleException(
      TagAlreadyExistsException exception) {
    SingleExceptionResponse response =
        prepareSingleExceptionResponse(TAG_ALREADY_EXIST_CODE, exception);
    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
  }

  @ExceptionHandler
  public ResponseEntity<SingleExceptionResponse> handleException(DeletingTagException exception) {
    SingleExceptionResponse response = prepareSingleExceptionResponse(DELETING_TAG_CODE, exception);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<MultipleExceptionResponse> handleException(
      EntityNotValidMultipleException exception) {
    MultipleExceptionResponse response =
        prepareMultipleExceptionResponse(ENTITY_NOT_VALID_MULTIPLE_FIELDS_CODE, exception);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<SingleExceptionResponse> handleException(
      EntityNotValidException exception) {
    SingleExceptionResponse response =
        prepareSingleExceptionResponse(ENTITY_NOT_VALID_SINGLE_FIELD_CODE, exception);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<SingleExceptionResponse> handleException(
      InsufficientFundInAccount exception) {
    SingleExceptionResponse response =
        prepareSingleExceptionResponse(INSUFFICIENT_FUND_IN_ACCOUNT_CODE, exception);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<SingleExceptionResponse> handleException(EmptyOrderException exception) {
    SingleExceptionResponse response = prepareSingleExceptionResponse(EMPTY_ORDER_CODE, exception);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<SingleExceptionResponse> handleException(
      UserDoesNotHaveOrderException exception) {
    SingleExceptionResponse response =
        prepareSingleExceptionResponse(USER_DOES_NOT_HAVE_ORDER_CODE, exception);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<MultipleExceptionResponse> handleException(
      PageNotValidException exception) {
    MultipleExceptionResponse response =
        prepareMultipleExceptionResponse(PAGE_NOT_VALID_CODE, exception);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<SingleExceptionResponse> handleException(
      DeletingGiftCertificateException exception) {
    SingleExceptionResponse response =
        prepareSingleExceptionResponse(DELETING_CERTIFICATE_CODE, exception);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<SingleExceptionResponse> handleException(
      UserLoginNotFoundException exception) {
    SingleExceptionResponse response =
        prepareSingleExceptionResponse(USER_LOGIN_NOT_FOUND_CODE, exception);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<SingleExceptionResponse> handleException(
      EntityNotSavedException exception) {
    SingleExceptionResponse response =
        prepareSingleExceptionResponse(ENTITY_NOT_SAVED_CODE, exception);
    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
  }

  @ExceptionHandler
  public ResponseEntity<SingleExceptionResponse> handleException(
      UserLoginExistsException exception) {
    SingleExceptionResponse response =
        prepareSingleExceptionResponse(USER_ALREADY_EXIST_CODE, exception);
    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      HttpMediaTypeNotSupportedException exception,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    SingleExceptionResponse response = new SingleExceptionResponse();
    response.setErrorMessage(translatorLocale.toLocale("exception.message.41501"));
    response.setErrorCode(HTTP_MEDIA_TYPE_NOT_SUPPORTED_CODE);
    return new ResponseEntity<>(response, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
  }

  @Override
  protected ResponseEntity<Object> handleTypeMismatch(
      TypeMismatchException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
    SingleExceptionResponse response = new SingleExceptionResponse();
    response.setErrorMessage(translatorLocale.toLocale("exception.message.40008"));
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
    response.setErrorMessage(translatorLocale.toLocale("exception.message.noHandler"));
    response.setErrorCode(NO_HANDLER_FOUND_CODE);
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  private SingleExceptionResponse prepareSingleExceptionResponse(
      int errorCode, Exception exception) {
    SingleExceptionResponse response = new SingleExceptionResponse();
    response.setErrorMessage(exception.getMessage());
    response.setErrorCode(errorCode);
    return response;
  }

  private MultipleExceptionResponse prepareMultipleExceptionResponse(
      int errorCode, Exception exception) {
    MultipleExceptionResponse response = new MultipleExceptionResponse();
    List<String> exceptionList = new ArrayList<>(Arrays.asList(exception.getMessage().split("\n")));
    response.setErrorMessages(exceptionList);
    response.setErrorCode(errorCode);
    return response;
  }
}
