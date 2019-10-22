package com.darakay.micro689.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "passport_info_bl")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassportInfoBLRecord implements BlackListRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Integer id;

    @Getter
    private Integer creatorId;

    @Size(max = 4, min = 4, message = "Серия пасспорта может содержать только 4 знака")
    @Column(name = "pass_ser")
    private String passportSeria;

    @Size(max = 6, min = 6, message = "Номер пасспорта может содержать только 6 знаков")
    @Column(name = "pass_num")
    private String passportNumber;

    public PassportInfoBLRecord(int creatorId) {
        this.creatorId = creatorId;
    }
}
