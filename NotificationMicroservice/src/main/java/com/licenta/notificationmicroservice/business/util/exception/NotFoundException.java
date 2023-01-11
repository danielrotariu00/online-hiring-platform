package com.licenta.notificationmicroservice.business.util.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ExceptionWithStatus {

    public NotFoundException(String errorMessage) {
        super(errorMessage, HttpStatus.NOT_FOUND);
    }
}
