package com.jun.service.account.domain.exceptions;

import com.jun.service.account.domain.exceptions.info.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<?> resourceNotFoundException(
      ResourceNotFoundException ex, WebRequest request) {
    //    ErrorDetails errorDetails =
    //        new ErrorDetails(
    //            new Date(),
    //            ex.getMessage(),
    //            ((ServletWebRequest) request).getRequest().getServletPath());
    return new ResponseEntity<>(
        ErrorResponse.resourceNotFound(ex.getMessage()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({
    BadRequestException.class,
    TransactionAlreadyExistException.class,
    AccountAlreadyLinkedException.class
  })
  public ResponseEntity<?> badRequestException(BadRequestException ex, WebRequest request) {
    //    ErrorDetails errorDetails =
    //        new ErrorDetails(
    //            new Date(),
    //            ex.getMessage(),
    //            ((ServletWebRequest) request).getRequest().getServletPath());
    return new ResponseEntity<>(ErrorResponse.badRequest(ex.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AccountAlreadyExistsException.class)
  public ResponseEntity<?> accountAlreadyExistsException() {
    return new ResponseEntity<>(ErrorResponse.accountAlreadyExists(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AccountRegisteredNotActivatedException.class)
  public ResponseEntity<?> accountRegisteredNotActivatedException() {
    return new ResponseEntity<>(
        ErrorResponse.accountRegisteredNotActivated(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AccountRetypePasswordNotMatchException.class)
  public ResponseEntity<?> retypePasswordNotMatchException() {
    return new ResponseEntity<>(ErrorResponse.retypePasswordNotMatch(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AccountHasBeenActivatedException.class)
  public ResponseEntity<?> accountHasBeenActivatedException() {
    return new ResponseEntity<>(ErrorResponse.accountHasBeenActivated(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AccountNotExistsException.class)
  public ResponseEntity<?> accountNotExistsException() {
    return new ResponseEntity<>(ErrorResponse.accountNotExists(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({WrongAccountOrPasswordException.class})
  public ResponseEntity<?> wrongAccountOrPasswordException() {
    return new ResponseEntity<>(ErrorResponse.wrongAccountOrPassword(), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(AccessTokenNotValidException.class)
  public ResponseEntity<?> badRequestException(
      AccessTokenNotValidException ex, WebRequest request) {
    return new ResponseEntity<>(ErrorResponse.tokenNotValid(), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(AuthenticationCodeNotExistsException.class)
  public ResponseEntity<?> authenticationCodeNotExistsException() {
    return new ResponseEntity<>(
        ErrorResponse.authenticationCodeNotExists(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(SuccessException.class)
  public ResponseEntity<?> successException(SuccessException ex, WebRequest request) {
    return new ResponseEntity<>(ErrorResponse.sendCodeSuccess(ex.getMessage()), HttpStatus.OK);
  }

  @ExceptionHandler(MatchOriginEmailException.class)
  public ResponseEntity<?> matchOriginEmailException() {
    return new ResponseEntity<>(ErrorResponse.matchOriginEmail(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MatchOriginPhoneNumberException.class)
  public ResponseEntity<?> matchOriginPhoneNumberException() {
    return new ResponseEntity<>(ErrorResponse.matchOriginPhoneNumber(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(PhoneNumberAlreadyUseException.class)
  public ResponseEntity<?> phoneNumberAlreadyUseException() {
    return new ResponseEntity<>(ErrorResponse.phoneNumberAlreadyUse(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(EmailAlreadyUseException.class)
  public ResponseEntity<?> emailAlreadyUseException() {
    return new ResponseEntity<>(ErrorResponse.emailAlreadyUse(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({
    ExpiredJwtException.class,
    UnsupportedJwtException.class,
    MalformedJwtException.class,
    SignatureException.class,
    IllegalArgumentException.class,
    UnauthorizedException.class
  })
  public ResponseEntity<?> unauthorizedException() {
    return new ResponseEntity<>(ErrorResponse.tokenNotValid(), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
    //    ErrorDetails errorDetails =
    //        new ErrorDetails(
    //            new Date(),
    //            ex.getMessage(),
    //            ((ServletWebRequest) request).getRequest().getServletPath());
    //    log.debug("error", ex);
    Sentry.capture(ex);
    log.info(ex.getMessage());
    return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Override
  public ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    Map<String, String> invalidField = new HashMap<>();
    if (ex instanceof MethodArgumentNotValidException) {
      List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
      for (FieldError fieldError : fieldErrors) {
        invalidField.put(fieldError.getField(), fieldError.getDefaultMessage());
      }
    } else {
      invalidField.put("default", ex.getLocalizedMessage());
    }
    String message = invalidField.entrySet().iterator().next().getValue();
    return new ResponseEntity<>(
        ErrorResponse.notValid(message, invalidField), new HttpHeaders(), HttpStatus.BAD_REQUEST);
  }
}
