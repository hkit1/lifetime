package com.hkit.lifetime.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid", nullable = false)
    private UUID uuid;
    private String id;
    private String pw;
    private String birth;
    private String email;
    private String tel;
    private String sex;
    private String address1;
    private String address2;
}