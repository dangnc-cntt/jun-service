package com.jun.service.account.domain.exceptions;

public class TransactionAlreadyExistException extends RuntimeException {
  public TransactionAlreadyExistException(String exception) {
    super(exception);
  }
}
