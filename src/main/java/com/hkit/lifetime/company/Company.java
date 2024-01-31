package com.hkit.lifetime.company;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "company")
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue
    @Column(name = "companyId", nullable = false)
    private String companyId;

    @Column(name = "id", nullable = false, length = 15)
    private String id;

    @Lob
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "admin", nullable = false, length = 10)
    private String admin;

    @Lob
    @Column(name = "email", nullable = false)
    private String email;

    @Lob
    @Column(name = "address1", nullable = false)
    private String address1;

    @Lob
    @Column(name = "address2")
    private String address2;

    @Column(name = "tel", nullable = false, length = 12)
    private String tel;

    public Company(String companyId, String id, String name, String admin, String email, String address1, String address2, String tel) {
        this.companyId = companyId;
        this.id = id;
        this.name = name;
        this.admin = admin;
        this.email = email;
        this.address1 = address1;
        this.address2 = address2;
        this.tel = tel;
    }

    public static Company toCompany(CompanyDto companyDto) {
    return new Company(
        companyDto.companyId(),
        companyDto.id(),
        companyDto.name(),
        companyDto.admin(),
        companyDto.email(),
        companyDto.address1(),
        companyDto.address2(),
        companyDto.tel());
    }
}