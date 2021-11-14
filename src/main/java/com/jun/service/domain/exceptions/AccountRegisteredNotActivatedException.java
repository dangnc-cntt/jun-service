package com.jun.service.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class AccountRegisteredNotActivatedException extends RuntimeException {

    public AccountRegisteredNotActivatedException(String message) {
        super(message);
    }
}
