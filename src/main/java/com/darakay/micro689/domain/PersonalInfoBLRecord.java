package com.darakay.micro689.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "personal_info_bl")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalInfoBLRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer creatorId;
    private String surname;
    private String firstName;
    private String secondName;
    private Date birthDate;
}
