package com.darakay.micro689.dto;

import com.darakay.micro689.exception.InvalidFindMatchesRequestFormatException;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("personalInfo")
    private PersonalInfoDTO personalInfo;

    @JsonProperty("passportInfo")
    private PassportInfoDTO passportInfo;

    @JsonProperty("inn")
    private String inn;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("email")
    private String email;

    @JsonAnySetter
    public void handleUnknown(String key, Object value){
        throw InvalidFindMatchesRequestFormatException.invalidFormat();
    }
}
