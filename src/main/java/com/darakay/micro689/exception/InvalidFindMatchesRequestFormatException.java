package com.darakay.micro689.exception;

public class InvalidFindMatchesRequestFormatException extends RuntimeException {
    public InvalidFindMatchesRequestFormatException(String message) {
        super(message);
    }

    public static InvalidFindMatchesRequestFormatException missingRequiredField(String fieldName) {
        return new InvalidFindMatchesRequestFormatException("Не заполнено обязательное поле в '" + fieldName + "'");
    }

    public static InvalidFindMatchesRequestFormatException invalidFormat() {
        return new InvalidFindMatchesRequestFormatException("Некорректный формат запроса");
    }
}
