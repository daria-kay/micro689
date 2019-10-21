package com.darakay.micro689.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "passport_info_bl")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassportInfoBLRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer creatorId;
    @Column(name = "pass_ser")
    private String passportSeria;
    @Column(name = "pass_num")
    private String passportNumber;
}
