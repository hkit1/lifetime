package com.hkit.lifetime.account;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {
    @Id
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "pw")
    private String pw;

    @Column(name = "birth")
    private LocalDate birth;

    @Column(name = "email")
    private String email;

    @Column(name = "tel")
    private String tel;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

}