package com.darakay.micro689.dto;

import com.darakay.micro689.domain.Record;
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
        throw InvalidFindMatchesRequestFormatException.invalidFormat();
    }

    public BlackListRecordDTO(Record record){
        this.personalInfo = new PersonalInfoDTO(record.getPersonalInfo());
        this.passportInfo = new PassportInfoDTO(record.getPassportInfo());
        if(record.getEmail() != null)
            this.email = record.getEmail().getEmail();
        if(record.getInn() != null)
            this.inn = record.getInn().getInn();
        if(record.getPhone() != null)
            this.phone = record.getPhone().getPhone();
        this.id = record.getId();
    }
}
