package com.darakay.micro689.exception;

public class CannotReadFileException extends RuntimeException {
    public CannotReadFileException(String message) {
        super(message);
    }

    public static CannotReadFileException emptyRecordList() {
        return new CannotReadFileException("Нет записей");
    }
}
