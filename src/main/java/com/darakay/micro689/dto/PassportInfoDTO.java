package com.darakay.micro689.dto;

import com.darakay.micro689.domain.PassportInfoBLRecord;
import com.darakay.micro689.exception.InvalidRequestFormatException;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@JsonAutoDetect
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PassportInfoDTO {

    @JsonProperty("passportSeria")
    private String passportSeria;

    @JsonProperty("passportNumber")
    private String passportNumber;

    public PassportInfoDTO(PassportInfoBLRecord record) {
        this.passportSeria = record.getPassportSeria();
        this.passportNumber = record.getPassportNumber();
    }
}
