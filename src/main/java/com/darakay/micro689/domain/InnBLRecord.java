package com.darakay.micro689.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

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

    @Pattern(regexp = "^[\\d]{10}([\\d]{2})?$", message = "Некорректная длина или формат ИНН")
    @Column(name = "inn_num")
    private String inn;
}
