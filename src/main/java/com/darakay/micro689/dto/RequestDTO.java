package com.darakay.micro689.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Map;

@JsonAutoDetect
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {
    @JsonProperty("fields")
    private Map<String, String> fields;
}
