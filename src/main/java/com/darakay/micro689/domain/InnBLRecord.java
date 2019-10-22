package com.darakay.micro689.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "inn_bl")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InnBLRecord {
    private final static int CSV_COLUMN_COUNT = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Integer id;

    private Integer creatorId;

    @Size(max = 6, min = 6, message = "Номер ИНН может содержать только 6 знаков")
    @Column(name = "inn_num")
    private String inn;

    public InnBLRecord(int creatorId) {
        this.creatorId = creatorId;
    }
}
