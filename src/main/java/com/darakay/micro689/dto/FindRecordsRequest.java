package com.darakay.micro689.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "Фамилия")
    private String surname;

    @ApiModelProperty(value = "Имя")
    private String firstName;

    @ApiModelProperty(value = "Отчество")
    private String secondName;

    @ApiModelProperty(value = "Дата рождения")
    private Date birthDate;

    @ApiModelProperty(value = "Серия пасспорта")
    private String passportSeria;

    @ApiModelProperty(value = "Номер пасспорта")
    private String passportNumber;

    @ApiModelProperty(value = "Номер ИНН")
    private String inn;

    @ApiModelProperty(value = "Телефон")
    private String phone;

    @ApiModelProperty(value = "Почта")
    private String email;
}
