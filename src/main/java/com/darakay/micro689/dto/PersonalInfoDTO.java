package com.darakay.micro689.dto;

import com.darakay.micro689.deserializer.PersonalInfoDeserializer;
import com.darakay.micro689.domain.PersonalInfoBLRecord;
import com.darakay.micro689.validation.FindMatchesRequestValidator;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@JsonAutoDetect
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = PersonalInfoDeserializer.class)
public class PersonalInfoDTO {

    @ApiModelProperty(value = "Фамилия", required = true)
    private String surname;

    @ApiModelProperty(value = "Имя", required = true)
    private String firstName;

    @ApiModelProperty(value = "Отчество", required = true)
    private String secondName;

    @ApiModelProperty(value = "Дата рождения", required = true)
    private Date birthDate;

    PersonalInfoDTO(PersonalInfoBLRecord record){
        if(record != null) {
            this.surname = record.getSurname();
            this.firstName = record.getFirstName();
            this.secondName = record.getSecondName();
            this.birthDate = record.getBirthDate();
        }
    }
}
