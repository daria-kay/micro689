package com.darakay.micro689.domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Table(name = "full_filled_bl")
@Entity
public class FullFilledBLRecord {
    @Id
    private Integer id;
    private Integer creatorId;
    private String surname;
    private String firstName;
    private String secondName;
    private Date birthDate;
    private String passportSeria;
    private String passportNumber;
    private String phone;
    private String email;

}
