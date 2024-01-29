package com.hkit.lifetime.teacher;

import com.hkit.lifetime.account.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @OneToOne
    @JoinColumn(name = "account", nullable = false)
    private Account account;

    @Column(name = "name")
    private String name;

    @Column(name = "tel")
    private String tel;
}