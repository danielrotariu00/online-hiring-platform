package com.licenta.searchmicroservice.presentation.util

import com.licenta.searchmicroservice.business.util.exception.ExceptionWithStatus
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@ControllerAdvice
class ExceptionTranslator {

    @ExceptionHandler(ExceptionWithStatus::class)
    fun translate(ex: ExceptionWithStatus): ResponseEntity<String?>? {
        return ResponseEntity<String?>(ex.message, ex.status)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun translate(ex: MethodArgumentTypeMismatchException): ResponseEntity<String?>? {
        return ResponseEntity<String?>("Incorrect input.", HttpStatus.BAD_REQUEST)
    }

    // @ExceptionHandler(RuntimeException.class)
    fun translate(): ResponseEntity<String?>? {
        return ResponseEntity("An error occurred while processing your request", HttpStatus.INTERNAL_SERVER_ERROR)
    }
}