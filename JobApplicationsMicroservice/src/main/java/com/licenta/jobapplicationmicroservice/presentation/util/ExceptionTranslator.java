package com.licenta.jobapplicationmicroservice.presentation.util;

import com.licenta.jobapplicationmicroservice.business.util.exception.ExceptionWithStatus;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class ExceptionTranslator {

    private final ValidationErrorMapper validationErrorMapper = Mappers.getMapper(ValidationErrorMapper.class);

    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ValidationError> translate(MethodArgumentNotValidException ex) {

        return  ex.getBindingResult().getFieldErrors().stream()
                .map(validationErrorMapper::toValidationError)
                .collect(Collectors.toList());
    }

    @ExceptionHandler(ExceptionWithStatus.class)
    public ResponseEntity<String> translate(ExceptionWithStatus ex) {

        return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    }

    // @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> translate() {

        return new ResponseEntity<>("An error occurred while processing your request", INTERNAL_SERVER_ERROR);
    }
}
