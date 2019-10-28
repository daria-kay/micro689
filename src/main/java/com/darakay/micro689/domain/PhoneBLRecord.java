package com.darakay.micro689.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "phone_bl")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class PhoneBLRecord implements BlackListRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 12, message = "Максимум 12 знаков")
    private String phone;
}
