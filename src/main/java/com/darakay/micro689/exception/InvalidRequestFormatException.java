package com.darakay.micro689.exception;

public class InvalidRequestFormatException extends RuntimeException {
    public InvalidRequestFormatException(String message) {
        super(message);
    }

    public static InvalidRequestFormatException missingRequiredField(String fieldName) {
        return new InvalidRequestFormatException("Не заполнено обязательное поле '" + fieldName + "'");
    }
}
