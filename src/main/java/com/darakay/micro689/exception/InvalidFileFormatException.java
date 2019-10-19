package com.darakay.micro689.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidFileFormatException extends RuntimeException {
    public InvalidFileFormatException(String message) {
        super(message);
    }

    public static InvalidFileFormatException wrongFieldCount(int expectedCount, int actualCount) {
        return new InvalidFileFormatException(
                String.format("Wrong number of csv field! Expected %s, but number is %s", expectedCount, actualCount));
    }

    public static InvalidFileFormatException wrongDateFormat() {
        return new InvalidFileFormatException("Wrong date format! Expected yyyy-[m]m-[d]d");
    }
}
