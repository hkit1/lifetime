package com.hkit.lifetime.company;

import com.hkit.lifetime.account.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "company")
public class Company {
    @Id
    @Column(name = "name", nullable = false, length = 30)
    private String name;
}