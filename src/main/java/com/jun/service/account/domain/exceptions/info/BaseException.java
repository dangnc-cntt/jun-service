package com.jun.service.account.domain.exceptions.info;

import lombok.Data;

@Data
public class BaseException extends RuntimeException {

  protected int code;

  public BaseException(int code, String message) {
    super(message);
    setCode(code);
  }
}
