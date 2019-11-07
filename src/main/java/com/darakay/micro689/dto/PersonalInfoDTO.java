package com.darakay.micro689.dto;

import com.darakay.micro689.domain.PersonalInfoBLRecord;
import com.darakay.micro689.exception.InvalidFindMatchesRequestFormatException;
import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@JsonAutoDetect
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonalInfoDTO {

    @JsonIgnore
    private DateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd");

    @ApiModelProperty(value = "Фамилия", required = true)
    @JsonProperty("surname")
    private String surname;

    @ApiModelProperty(value = "Имя", required = true)
    @JsonProperty("firstName")
    private String firstName;

    @ApiModelProperty(value = "Отчество", required = true)
    @JsonProperty("secondName")
    private String secondName;

    @ApiModelProperty(value = "Дата рождения", required = true)
    private Date birthDate;

    @JsonSetter("birthDate")
     public void setDate(String date){
        String[] parts = date.split("-");
        if(parts.length == 3){
            int m = Integer.parseInt(parts[1]);
            int d = Integer.parseInt(parts[2]);
            if(m > 12 || d > 31)
                throw InvalidFindMatchesRequestFormatException.invalidDate();
            try{
                this.birthDate = new Date(dateTimeFormatter.parse(date).getTime());
            } catch (ParseException e) {
                throw InvalidFindMatchesRequestFormatException.invalidDate();
            }
        } else {
            this.birthDate = new Date(Long.parseLong(date));
        }

    }

    public PersonalInfoDTO(String surname, String firstName, String secondName, Date birthDate) {
        this.surname = surname;
        this.firstName = firstName;
        this.secondName = secondName;
        this.birthDate = birthDate;
    }

    PersonalInfoDTO(PersonalInfoBLRecord record){
        if(record != null) {
            this.surname = record.getSurname();
            this.firstName = record.getFirstName();
            this.secondName = record.getSecondName();
            this.birthDate = record.getBirthDate();
        }
    }
}
