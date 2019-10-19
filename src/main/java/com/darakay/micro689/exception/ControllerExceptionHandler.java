package com.darakay.micro689.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(InvalidFileFormatException.class)
    public ResponseEntity<ErrorMessage> handleInvalidFileFormatException(InvalidFileFormatException ex){
        return ResponseEntity.badRequest().body(new ErrorMessage(ex.getMessage()));
    }
}
