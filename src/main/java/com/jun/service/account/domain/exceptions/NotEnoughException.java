package com.jun.service.account.domain.exceptions;

public class NotEnoughException extends RuntimeException {
  public NotEnoughException(String exception) {
    super(exception);
  }
}
