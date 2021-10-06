package com.jun.service.account.domain.exceptions;

import com.jun.service.account.domain.exceptions.info.BaseException;
import com.jun.service.account.domain.exceptions.info.ErrorCode;

public class PhoneCardErrorException extends BaseException {

  public PhoneCardErrorException() {
    super(ErrorCode.PHONE_CARD_ERROR, "Error request");
  }

  public PhoneCardErrorException(String message) {
    super(ErrorCode.PHONE_CARD_ERROR, message);
  }
}
