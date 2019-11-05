package com.darakay.micro689.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.intellij.lang.annotations.RegExp;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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

    @Size(max = 20, min = 1, message = "Логин не может содержать больше 20 символов")
    @Pattern(regexp = "[a-zA-Z0-9_]+", message = "Логин может содержать только латинские буквы, цифры и нижнее подчеркивание")
    private String login;

    @Column(name = "pw")
    private String password;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @Column(name = "block_flag")
    private boolean block;
}
