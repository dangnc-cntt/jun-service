package com.jun.service.account.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class SuccessException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public SuccessException(String exception) {
    super(exception);
  }
}
