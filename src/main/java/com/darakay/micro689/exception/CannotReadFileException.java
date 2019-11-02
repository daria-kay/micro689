package com.darakay.micro689.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Cannot parse file")
public class CannotReadFileException extends RuntimeException {
    public CannotReadFileException(String message) {
        super(message);
    }

    public static CannotReadFileException emptyRecordList() {
        return new CannotReadFileException("Нет записей");
    }
}
