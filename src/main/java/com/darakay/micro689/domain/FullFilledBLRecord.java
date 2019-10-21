package com.darakay.micro689.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Table(name = "full_filled_bl")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullFilledBLRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Setter
    private Integer creatorId;
    private String surname;
    private String firstName;
    private String secondName;
    private Date birthDate;
    @Column(name = "pass_ser")
    private String passportSeria;
    @Column(name = "pass_num")
    private String passportNumber;
    @Column(name = "inn_num")
    private String inn;
    private String phone;
    private String email;
}
