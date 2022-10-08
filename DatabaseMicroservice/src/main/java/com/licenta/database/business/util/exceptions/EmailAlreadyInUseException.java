package com.licenta.database.business.util.exceptions;

import org.springframework.http.HttpStatus;

public class EmailAlreadyInUseException extends ExceptionWithStatus {

    public EmailAlreadyInUseException(String errorMessage) {
        super(errorMessage, HttpStatus.CONFLICT);
    }
}
