package com.licenta.database.business.util.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidInputException extends ExceptionWithStatus {

    public InvalidInputException(String errorMessage) {
        super(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
