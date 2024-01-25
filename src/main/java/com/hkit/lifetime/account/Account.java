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
    @Lob
    @Column(name = "unique_id")
    private String uniqueId;

    @Lob
    @Column(name = "id", nullable = false)
    private String id;

    @Lob
    @Column(name = "pw")
    private String pw;

    @Column(name = "birth")
    private LocalDate birth;

    @Lob
    @Column(name = "email")
    private String email;

    @Lob
    @Column(name = "tel")
    private String tel;

    @Column(name = "gender")
    private Integer gender;

    @Lob
    @Column(name = "address1")
    private String address1;

    @Lob
    @Column(name = "address2")
    private String address2;

}