package com.darakay.micro689.dto;

import com.darakay.micro689.exception.InvalidFindMatchesRequestFormatException;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@JsonAutoDetect
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FindRecordsRequest {
    private String surname;
    private String firstName;
    private String secondName;
    private Date birthDate;
    private String passportSeria;
    private String passportNumber;
    private String inn;
    private String phone;
    private String email;
}
