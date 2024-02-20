package com.hkit.lifetime.account;

import com.hkit.lifetime.security.SecurityRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "pw", nullable = false)
    private String pw;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "tel", nullable = false, length = 15)
    private String tel;

    @Column(name = "gender", nullable = false)
    private Boolean gender = false;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private SecurityRole role;

    @Column(name = "address1", nullable = false)
    private String address1;

    @Column(name = "address2")
    private String address2;

    @Column(name = "created_at")
    private LocalDate created_at;

    public Account(String uuid, String name, String id, String pw, LocalDate birth, String email, String tel, Boolean gender, String address1, String address2) {
        this.uuid = uuid;
        this.name = name;
        this.id = id;
        this.pw = pw;
        this.birth = birth;
        this.email = email;
        this.tel = tel;
        this.gender = gender;
        this.address1 = address1;
        this.address2 = address2;
        this.role = SecurityRole.USER;
        this.created_at = LocalDate.now();
    }

    public static Account toAccount(AccountDto accountDto) {
        return new Account(null,
                accountDto.name(),
                accountDto.id(),
                accountDto.pw(),
                LocalDate.parse(accountDto.birth(), DateTimeFormatter.ofPattern("yyyyMMdd")),
                accountDto.email(),
                accountDto.tel(),
                accountDto.gender(),
                accountDto.address1(),
                accountDto.address2());
    }

    public void updateAccount(AccountDto accountDto) {
        this.pw = accountDto.pw();
        this.email = accountDto.email();
        this.tel = accountDto.tel();
        this.address1 = accountDto.address1();
        this.address2 = accountDto.address2();
    }

    public void updateAccountRole(SecurityRole role) {
        this.role = role;
    }

}