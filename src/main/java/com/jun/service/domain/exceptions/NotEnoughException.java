package com.jun.service.domain.exceptions;

public class NotEnoughException extends RuntimeException {
  public NotEnoughException(String exception) {
    super(exception);
  }
}
