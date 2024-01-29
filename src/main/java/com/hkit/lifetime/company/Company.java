package com.hkit.lifetime.company;

import com.hkit.lifetime.account.Account;
import com.hkit.lifetime.account.AccountDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "name", nullable = false, length = 30)
    private String name;

    public Company(String name) {
        this.name = name;
    }

    public static Company toCompany(CompanyDto companyDto) {
        return new Company(companyDto.name());
    }
}