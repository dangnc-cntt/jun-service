package com.jun.service.domain.exceptions.info;

public class ErrorCode {
  public static final int USER_REGISTERED_NOT_ACTIVATED = 1001;
  public static final int ACCOUNT_ALREADY_EXISTS = 1002;
  public static final int RETYPE_PASSWORD_NOT_MATCH = 1003;
  public static final int ACCOUNT_HAS_BEEN_ACTIVATED = 1004;
  public static final int ACCOUNT_NOT_EXISTS = 1005;
  public static final int AUTHENTICATION_CODE_NOT_EXISTS = 1006;
  public static final int RESOURCE_NOT_FOUND = 1007;
  public static final int UNAUTHORIZED = 1008;
  public static final int BAD_REQUEST = 1009;
  public static final int NOT_VALID = 1010;
  public static final int SUCCESS = 1000;
  public static final int MATCH_ORIGIN_PHONE_NUMBER = 1011;
  public static final int MATCH_ORIGIN_EMAIL = 1012;
  public static final int PHONE_NUMBER_ALREADY_USE = 1013;
  public static final int EMAIL_ALREADY_USE = 1013;
  public static final int ACCOUNT_ALREADY_LINKED = 1014;

  public static final int LUCKYSPIN_ERROR = 1015;
  public static final int PHONE_CARD_ERROR = 1016;
}
