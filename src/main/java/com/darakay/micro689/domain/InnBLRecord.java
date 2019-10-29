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
@Getter
public class InnBLRecord implements BlackListRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 10, min = 6, message = "Номер не ИНН может содержать больше 10 знаков")
    @Column(name = "inn_num")
    private String inn;
}
