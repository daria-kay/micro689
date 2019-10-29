package com.darakay.micro689.dto;

import com.darakay.micro689.exception.InvalidRequestFormatException;
import com.fasterxml.jackson.annotation.*;
import lombok.Builder;
import lombok.Getter;

@JsonAutoDetect
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlackListRecordDTO {
    @JsonProperty("id")
    private Integer id;

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
        throw InvalidRequestFormatException.invalidFormat();
    }
}
