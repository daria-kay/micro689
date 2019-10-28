package com.darakay.micro689.dto;

import com.darakay.micro689.exception.InvalidRequestFormatException;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.sql.Date;

@JsonAutoDetect
@Getter
public class PersonalInfoDTO {

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("secondName")
    private String secondName;

    @JsonProperty("birthDate")
    private Date birthDate;

    @JsonCreator
    public PersonalInfoDTO(String surname, String firstName, String secondName, Date birthDate) {
        if(surname == null || firstName == null || secondName == null || birthDate == null)
            throw InvalidRequestFormatException.missingRequiredField("personalInfo");
        this.surname = surname;
        this.firstName = firstName;
        this.secondName = secondName;
        this.birthDate = birthDate;
    }
}
