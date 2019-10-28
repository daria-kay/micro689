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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personal_id", referencedColumnName = "id")
    private PersonalInfoBLRecord personalInfo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passport_id", referencedColumnName = "id")
    private PassportInfoBLRecord passportInfo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inn_id", referencedColumnName = "id")
    private InnBLRecord inn;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "phone_id", referencedColumnName = "id")
    private PhoneBLRecord phone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "email_id", referencedColumnName = "id")
    private EmailBLRecord email;
}
