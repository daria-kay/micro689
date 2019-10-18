package com.darakay.micro689.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BLTypeNotFoundException extends RuntimeException {
    public BLTypeNotFoundException(String unexistedBLType) {
        super("Black list type '" + unexistedBLType + "' not found!");
    }
}
