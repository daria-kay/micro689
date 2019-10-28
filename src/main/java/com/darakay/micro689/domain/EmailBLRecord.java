package com.darakay.micro689.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "email_bl")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EmailBLRecord implements BlackListRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 100, message = "Максимум 100 знаков")
    private String email;
}
