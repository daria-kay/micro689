package com.darakay.micro689.domain;

import javax.persistence.*;

@Entity
@Table(name = "user", schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String login;

    @Column(name = "pw")
    private String password;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @Column(name = "block_flag")
    private boolean block;
}
