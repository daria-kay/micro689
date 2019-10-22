package com.darakay.micro689.domain;


import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.sql.Date;

@Table(name = "full_filled_bl")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class FullFilledBLRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id;

    @Setter
    private Integer creatorId;

    @Size(max = 100, message = "Максимум 100 знаков")
    private String surname;

    @Size(max = 100, message = "Максимум 100 знаков")
    private String firstName;

    @Size(max = 100, message = "Максимум 100 знаков")
    private String secondName;

    private Date birthDate;

    @Size(max = 4, min = 4, message = "Серия пасспорта может содержать только 4 знака")
    @Column(name = "pass_ser")
    private String passportSeria;

    @Size(max = 6, min = 6, message = "Номер пасспорта может содержать только 6 знаков")
    @Column(name = "pass_num")
    private String passportNumber;

    @Size(max = 6, min = 6, message = "Номер ИНН может содержать только 6 знаков")
    @Column(name = "inn_num")
    private String inn;

    @Size(max = 12, message = "Максимум 12 знаков")
    private String phone;

    @Size(max = 100, message = "Максимум 100 знаков")
    private String email;

    public FullFilledBLRecord(Integer creatorId) {
        this.creatorId = creatorId;
    }
}
