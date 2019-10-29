package com.darakay.micro689.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@JsonAutoDetect
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LogupRequest {

    private String login;

    private String passwordHash;
    private int partnerId;
}
