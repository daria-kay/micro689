package com.darakay.micro689.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user", schema = "public")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
