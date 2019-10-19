package com.darakay.micro689.exception;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
@JsonAutoDetect
class ErrorMessage {
    @JsonProperty("message")
    private String message;
}
