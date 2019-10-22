package com.darakay.micro689.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;

@Entity
@Table(name = "personal_info_bl")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalInfoBLRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Integer id;

    private Integer creatorId;

    @Size(max = 100, message = "Максимум 100 знаков")
    private String surname;

    @Size(max = 100, message = "Максимум 100 знаков")
    private String firstName;

    @Size(max = 100, message = "Максимум 100 знаков")
    private String secondName;

    private Date birthDate;

    public PersonalInfoBLRecord(int creatorId) {
        this.creatorId = creatorId;
    }
}
