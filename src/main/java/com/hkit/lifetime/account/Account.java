package com.hkit.lifetime.account;

import com.hkit.lifetime.company.CompanyList;
import com.hkit.lifetime.lecture.LectureList;
import com.hkit.lifetime.oauth.Oauth;
import com.hkit.lifetime.rating.Rating;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "account")
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue
    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Lob
    @Column(name = "id", nullable = false)
    private String id;

    @Lob
    @Column(name = "pw", nullable = false)
    private String pw;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Lob
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "tel", nullable = false, length = 12)
    private String tel;

    @Column(name = "gender", nullable = false)
    private Boolean gender = false;

    @Lob
    @Column(name = "address1", nullable = false)
    private String address1;

    @Lob
    @Column(name = "address2")
    private String address2;

    @OneToMany(mappedBy = "uuid")
    private Set<Attendance> attendances = new LinkedHashSet<>();

    @OneToMany(mappedBy = "uuid")
    private Set<CompanyList> companyLists = new LinkedHashSet<>();

    @OneToMany(mappedBy = "uuid")
    private Set<LectureList> lectureLists = new LinkedHashSet<>();

    @OneToMany(mappedBy = "uuid")
    private Set<Oauth> oauths = new LinkedHashSet<>();

    @OneToMany(mappedBy = "uuid")
    private Set<Rating> ratings = new LinkedHashSet<>();

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
    }

    public static Account toAccount(AccountDto accountDto) {
        return new Account(null, accountDto.id(), accountDto.pw(), accountDto.name(), accountDto.birth(), accountDto.email(), accountDto.tel(), accountDto.gender(), accountDto.address1(), accountDto.address2());
    }

    public void updateAccount(AccountDto accountDto) {
        this.pw = accountDto.pw();
        this.email = accountDto.email();
        this.tel = accountDto.tel();
        this.address1 = accountDto.address1();
        this.address2 = accountDto.address2();
    }

}