package com.licenta.searchmicroservice.business.util.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExceptionWithStatus extends RuntimeException{

    public final HttpStatus status;

    @Builder
    public ExceptionWithStatus(String errorMessage, HttpStatus status) {
        super(errorMessage);

        this.status = status;
    }
}
