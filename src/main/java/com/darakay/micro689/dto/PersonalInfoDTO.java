package com.darakay.micro689.dto;

import com.darakay.micro689.domain.PersonalInfoBLRecord;
import com.darakay.micro689.exception.InvalidRequestFormatException;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@JsonAutoDetect
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PersonalInfoDTO {

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("secondName")
    private String secondName;

    @JsonProperty("birthDate")
    private Date birthDate;

    public PersonalInfoDTO(PersonalInfoBLRecord record){
        this.surname = record.getSurname();
        this.firstName = record.getFirstName();
        this.secondName = record.getSecondName();
        this.birthDate = record.getBirthDate();
    }
}
