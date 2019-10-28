package com.darakay.micro689.dto;

import com.darakay.micro689.annotation.ValidUserLogin;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

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
