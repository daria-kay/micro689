package com.darakay.micro689.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "inn_bl")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InnBLRecord {
    private final static int CSV_COLUMN_COUNT = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer creatorId;
    @Column(name = "inn_num")
    private String inn;
}
