package com.licenta.database.business.util.exceptions;

import org.springframework.http.HttpStatus;

public class FailedAuthenticationException extends ExceptionWithStatus {

    public FailedAuthenticationException(String errorMessage) {
        super(errorMessage, HttpStatus.UNAUTHORIZED);
    }
}
