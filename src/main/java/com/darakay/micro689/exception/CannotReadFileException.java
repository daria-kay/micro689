package com.darakay.micro689.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Cannot read file (IO Exception).")
public class CannotReadFileException extends RuntimeException {
}
