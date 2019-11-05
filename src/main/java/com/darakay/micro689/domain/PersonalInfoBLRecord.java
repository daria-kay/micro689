package com.darakay.micro689.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;

@Entity
@Table(name = "personal_info_bl")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class PersonalInfoBLRecord implements BlackListRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 100, min = 1, message = "Максимум 100 знаков")
    @Pattern(regexp = "[а-яА-Яa-zA-z]+", message = "Некорректная фамилия")
    private String surname;

    @Size(max = 100, message = "Максимум 100 знаков")
    @Pattern(regexp = "[а-яА-Яa-zA-z]+", message = "Некорректное имя")
    private String firstName;

    @Size(max = 100, message = "Максимум 100 знаков")
    @Pattern(regexp = "[а-яА-Яa-zA-z]+", message = "Некорректное отчество")
    private String secondName;

    private Date birthDate;
}
