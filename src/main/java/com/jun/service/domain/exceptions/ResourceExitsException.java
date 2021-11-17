package com.jun.service.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FOUND)
public class ResourceExitsException extends RuntimeException {
  public ResourceExitsException() {
    super("Resource Found");
  }

  public ResourceExitsException(String message) {
    super(message);
  }
}
