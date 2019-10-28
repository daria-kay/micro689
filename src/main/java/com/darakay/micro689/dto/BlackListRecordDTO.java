package com.darakay.micro689.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@JsonAutoDetect
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlackListRecordDTO {

    @JsonProperty("partnerId")
    private Integer partnerId;

    @JsonProperty("id")
    private int id;

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
}
