package com.darakay.micro689.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private User creator;

    @OneToOne
    @JoinColumn(name = "personal_id", referencedColumnName = "id")
    private PersonalInfoBLRecord personalInfo;

    @OneToOne
    @JoinColumn(name = "passport_id", referencedColumnName = "id")
    private PassportInfoBLRecord passportInfo;

    @OneToOne
    @JoinColumn(name = "inn_id", referencedColumnName = "id")
    private InnBLRecord inn;

    @OneToOne
    @JoinColumn(name = "phone_id", referencedColumnName = "id")
    private PhoneBLRecord phone;

    @OneToOne
    @JoinColumn(name = "email_id", referencedColumnName = "id")
    private EmailBLRecord email;
}
