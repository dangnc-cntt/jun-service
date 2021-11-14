package com.jun.service.domain.exceptions.info;

import com.jun.service.domain.exceptions.ErrorObject;
import com.jun.service.domain.utils.Message;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponse {

  public static ErrorObject accountRegisteredNotActivated() {
    return new ErrorObject(
        Message.ACCOUNT_REGISTERED_NOT_ACTIVATED,
        ErrorCode.USER_REGISTERED_NOT_ACTIVATED,
        new HashMap<>());
  }

  public static ErrorObject accountAlreadyExists() {
    return new ErrorObject(
        Message.ACCOUNT_ALREADY_EXISTS, ErrorCode.ACCOUNT_ALREADY_EXISTS, new HashMap<>());
  }

  public static ErrorObject accountAlreadyLinked(String message) {
    return new ErrorObject(message, ErrorCode.ACCOUNT_ALREADY_LINKED, new HashMap<>());
  }

  public static ErrorObject retypePasswordNotMatch() {
    return new ErrorObject(
        Message.RETYPE_PASSWORD_NOT_MATCH, ErrorCode.RETYPE_PASSWORD_NOT_MATCH, new HashMap<>());
  }

  public static ErrorObject accountHasBeenActivated() {
    return new ErrorObject(
        Message.ACCOUNT_HAS_BEEN_ACTIVATED, ErrorCode.ACCOUNT_HAS_BEEN_ACTIVATED, new HashMap<>());
  }

  public static ErrorObject accountNotExists() {
    return new ErrorObject(
        Message.ACCOUNT_NOT_FOUND, ErrorCode.ACCOUNT_NOT_EXISTS, new HashMap<>());
  }

  public static ErrorObject authenticationCodeNotExists() {
    return new ErrorObject(
        Message.AUTHENTICATION_CODE_NOT_FOUND,
        ErrorCode.AUTHENTICATION_CODE_NOT_EXISTS,
        new HashMap<>());
  }

  public static ErrorObject resourceNotFound(String message) {
    return new ErrorObject(message, ErrorCode.RESOURCE_NOT_FOUND, new HashMap<>());
  }

  public static ErrorObject notValid(String message, Map<String, String> invalidField) {
    return new ErrorObject(message, ErrorCode.NOT_VALID, invalidField);
  }

  public static ErrorObject badRequest(String message) {
    return new ErrorObject(message, ErrorCode.BAD_REQUEST, new HashMap<>());
  }

  public static ErrorObject wrongAccountOrPassword() {
    return new ErrorObject(
        Message.WRONG_ACCOUNT_OR_PASSWORD, ErrorCode.UNAUTHORIZED, new HashMap<>());
  }

  public static ErrorObject sendCodeSuccess(String message) {
    return new ErrorObject(message, ErrorCode.SUCCESS, new HashMap<>());
  }

  public static ErrorObject matchOriginPhoneNumber() {
    return new ErrorObject(
        Message.MATCH_ORIGIN_PHONE_NUMBER_MESSAGE,
        ErrorCode.MATCH_ORIGIN_PHONE_NUMBER,
        new HashMap<>());
  }

  public static ErrorObject matchOriginEmail() {
    return new ErrorObject(
        Message.MATCH_ORIGIN_EMAIL_MESSAGE, ErrorCode.MATCH_ORIGIN_EMAIL, new HashMap<>());
  }

  public static ErrorObject phoneNumberAlreadyUse() {
    return new ErrorObject(
        Message.PHONE_NUMBER_ALREADY_USE_MESSAGE,
        ErrorCode.PHONE_NUMBER_ALREADY_USE,
        new HashMap<>());
  }

  public static ErrorObject emailAlreadyUse() {
    return new ErrorObject(
        Message.EMAIL_ALREADY_USE_MESSAGE, ErrorCode.EMAIL_ALREADY_USE, new HashMap<>());
  }

  public static ErrorObject tokenNotValid() {
    return new ErrorObject(Message.UNAUTHORIZED, ErrorCode.UNAUTHORIZED, new HashMap<>());
  }
}
