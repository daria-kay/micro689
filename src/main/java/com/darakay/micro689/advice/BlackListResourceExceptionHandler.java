package com.darakay.micro689.advice;

import com.darakay.micro689.dto.ErrorMessageDTO;
import com.darakay.micro689.dto.FindMatchesResult;
import com.darakay.micro689.exception.CannotReadFileException;
import com.darakay.micro689.exception.InvalidFindMatchesRequestFormatException;
import com.darakay.micro689.exception.InvalidLogUpRequestException;
import com.darakay.micro689.exception.InvalidRecordFormatException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class BlackListResourceExceptionHandler {

    @ExceptionHandler(InvalidRecordFormatException.class)
    public ResponseEntity<ErrorMessageDTO> handleInvalidFileFormatException(InvalidRecordFormatException ex) {
        return ResponseEntity.badRequest().body(new ErrorMessageDTO(ex.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<ErrorMessageDTO> handleConstraintViolationException(ConstraintViolationException ex){
        String message = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.joining("\n"));
        return ResponseEntity.badRequest().body(new ErrorMessageDTO(message));
    }

    @ExceptionHandler(InvalidFindMatchesRequestFormatException.class)
    public ResponseEntity handleInvalidRequestFormatException(InvalidFindMatchesRequestFormatException ex){
        return ResponseEntity.ok(FindMatchesResult.error(ex.getMessage()));
    }

    @ExceptionHandler(InvalidLogUpRequestException.class)
    public ResponseEntity handleInvalidLogUpRequestException(InvalidLogUpRequestException ex){
        return ResponseEntity.badRequest().body(new ErrorMessageDTO(ex.getMessage()));
    }

    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity handleInvalidFindMatchesRequestJsonException(JsonMappingException ex){
        return ResponseEntity.ok(FindMatchesResult.error(ex.getCause().getMessage()));
    }

    @ExceptionHandler(CannotReadFileException.class)
    public ResponseEntity handleCannotReadFileException(CannotReadFileException ex){
        return ResponseEntity.badRequest().body(new ErrorMessageDTO(ex.getMessage()));
    }
}
