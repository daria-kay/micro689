package com.darakay.micro689.dto;

import com.darakay.micro689.domain.PassportInfoBLRecord;
import com.darakay.micro689.exception.InvalidRequestFormatException;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;



@JsonAutoDetect
@Builder
@Getter
public class PassportInfoDTO {

    @JsonProperty("passportSeria")
    private String passportSeria;

    @JsonProperty("passportNumber")
    private String passportNumber;

    @JsonCreator
    public PassportInfoDTO(String passportSeria, String passportNumber) {
        if(passportNumber == null || passportSeria == null)
            throw InvalidRequestFormatException.missingRequiredField("passportInfo");
        this.passportSeria = passportSeria;
        this.passportNumber = passportNumber;
    }

    public PassportInfoDTO(PassportInfoBLRecord record) {
        this.passportSeria = record.getPassportSeria();
        this.passportNumber = record.getPassportNumber();
    }
}
