package com.darakay.micro689.dto;

import com.darakay.micro689.domain.PassportInfoBLRecord;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@JsonAutoDetect
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class PassportInfoDTO {

    @JsonProperty("passportSeria")
    private String passportSeria;

    @JsonProperty("passportNumber")
    private String passportNumber;

    PassportInfoDTO(PassportInfoBLRecord record) {
        if(record != null) {
            this.passportSeria = record.getPassportSeria();
            this.passportNumber = record.getPassportNumber();
        }
    }
}
