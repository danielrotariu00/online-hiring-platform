package com.licenta.database.presentation.util;

import com.licenta.database.business.util.exceptions.ExceptionWithStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExceptionWithStatus.class)
    public ResponseEntity<String> handleConflict(ExceptionWithStatus ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleConflict() {
        return new ResponseEntity<>("An error occurred while processing your request", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}