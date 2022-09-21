package com.licenta.database.business.util.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ExceptionWithStatus {

    public NotFoundException(String errorMessage) {
        super(errorMessage, HttpStatus.NOT_FOUND);
    }
}
