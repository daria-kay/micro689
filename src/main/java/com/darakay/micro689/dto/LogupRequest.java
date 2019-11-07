package com.darakay.micro689.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("Логин пользователя. Должен быть уникальным")
    private String login;

    @ApiModelProperty("MD5 хэш пароля")
    private String passwordHash;

    @ApiModelProperty("ID ассоциированного партнера")
    private int partnerId;
}
