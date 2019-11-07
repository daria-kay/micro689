package com.darakay.micro689.dto;

import com.darakay.micro689.domain.Record;
import com.darakay.micro689.exception.InvalidFindMatchesRequestFormatException;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "ID записи")
    @JsonProperty("id")
    private Integer id;

    @ApiModelProperty(value = "ФИО и дата рождения")
    @JsonProperty("personalInfo")
    private PersonalInfoDTO personalInfo;

    @ApiModelProperty(value = "Информация о пасспорте")
    @JsonProperty("passportInfo")
    private PassportInfoDTO passportInfo;

    @ApiModelProperty(value = "Номер ИНН")
    @JsonProperty("inn")
    private String inn;

    @ApiModelProperty(value = "Номер телефона")
    @JsonProperty("phone")
    private String phone;

    @ApiModelProperty(value = "Почта")
    @JsonProperty("email")
    private String email;

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
