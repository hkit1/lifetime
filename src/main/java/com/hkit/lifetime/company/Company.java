package com.hkit.lifetime.company;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "company")
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "companyId", nullable = false)
    private String companyId;

    @Column(name = "id", nullable = false, length = 15)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "admin", nullable = false, length = 10)
    private String admin;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "address1", nullable = false)
    private String address1;

    @Column(name = "address2")
    private String address2;

    @Column(name = "tel", nullable = false, length = 15)
    private String tel;

    @Column(name = "authorized", nullable = false)
    private Boolean authorized;

    @Column(name = "authorized_date")
    private LocalDate authorizedDate;

    public Company(String id, String name, String admin, String email, String address1, String address2, String tel) {
        this.companyId = null;
        this.id = id;
        this.name = name;
        this.admin = admin;
        this.email = email;
        this.address1 = address1;
        this.address2 = address2;
        this.tel = tel;
        this.authorized = false;
        this.authorizedDate = null;
    }

    public static Company toCompany(CompanyDto companyDto) {
    return new Company(
        companyDto.id(),
        companyDto.name(),
        companyDto.admin(),
        companyDto.email(),
        companyDto.address1(),
        companyDto.address2(),
        companyDto.tel());
    }

    public void authorizationCompany(){
        this.authorized = true;
        this.authorizedDate = LocalDate.now();
    }

    public void deniedCompany(){
        this.authorizedDate = LocalDate.now();
    }
}