package com.jun.service.domain.exceptions;

public class TransactionAlreadyExistException extends RuntimeException {
  public TransactionAlreadyExistException(String exception) {
    super(exception);
  }
}
