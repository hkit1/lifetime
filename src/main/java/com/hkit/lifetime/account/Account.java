package com.hkit.lifetime.account;

import com.hkit.lifetime.security.SecurityRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Entity
@Table(name = "account")
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "pw", nullable = false)
    private String pw;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "tel", nullable = false)
    private String tel;

    @Column(name = "gender", nullable = false)
    private Integer gender;

    @Column(name = "address1", nullable = false)
    private String address1;

    @Column(name = "address2")
    private String address2;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SecurityRole role;

    @Column(name = "company")
    private String company;

    public Account(String uuid, String id, String pw, String name, String birth, String email, String tel, Integer gender, String address1, String address2, String role, String company) {
        this.uuid = uuid;
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.birth = LocalDate.parse(birth, DateTimeFormatter.BASIC_ISO_DATE);
        this.email = email;
        this.tel = tel;
        this.gender = gender;
        this.address1 = address1;
        this.address2 = address2;
        this.role = SecurityRole.valueOf(role);
        this.company = company;
    }

    public static Account toAccount(AccountDto accountDto) {
        return new Account(null, accountDto.id(), accountDto.pw(), accountDto.name(), accountDto.birth(), accountDto.email(), accountDto.tel(), accountDto.gender(), accountDto.address1(), accountDto.address2(), accountDto.role(), accountDto.company());
    }

    public void updateAccount(AccountDto accountDto) {
        this.pw = accountDto.pw();
        this.email = accountDto.email();
        this.tel = accountDto.tel();
        this.address1 = accountDto.address1();
        this.address2 = accountDto.address2();
    }

}