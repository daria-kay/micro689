package com.darakay.micro689.dto;

import com.darakay.micro689.exception.InvalidFindMatchesRequestFormatException;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonAutoDetect
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class ExampleDTO {
    @ApiModelProperty(value = "ФИО и дата рождения")
    @JsonProperty("personalInfo")
    private PersonalInfoDTO personalInfo;

    @ApiModelProperty(value = "Данные пасспорта")
    @JsonProperty("passportInfo")
    private PassportInfoDTO passportInfo;

    @ApiModelProperty(value = "Номер ИНН")
    @JsonProperty("inn")
    private String inn;

    @ApiModelProperty(value = "Номер телефона")
    @JsonProperty("phone")
    private String phone;

    @ApiModelProperty(value = "Почта")
    @JsonProperty("email")
    private String email;

    @JsonAnySetter
    public void handleUnknown(String key, Object value){
        throw InvalidFindMatchesRequestFormatException.invalidFormat();
    }
}
