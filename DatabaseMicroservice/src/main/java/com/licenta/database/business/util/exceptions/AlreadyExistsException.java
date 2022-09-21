package com.licenta.database.business.util.exceptions;

import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends ExceptionWithStatus {

    public AlreadyExistsException(String errorMessage) {
        super(errorMessage, HttpStatus.CONFLICT);
    }
}
