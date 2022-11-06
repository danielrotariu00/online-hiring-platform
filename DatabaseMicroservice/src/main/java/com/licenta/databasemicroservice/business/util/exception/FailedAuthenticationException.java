package com.licenta.databasemicroservice.business.util.exception;

import org.springframework.http.HttpStatus;

public class FailedAuthenticationException extends ExceptionWithStatus {

    public FailedAuthenticationException(String errorMessage) {
        super(errorMessage, HttpStatus.UNAUTHORIZED);
    }
}
