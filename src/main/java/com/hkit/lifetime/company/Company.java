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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uuid")
    private Account account;

    // api/company/add?name=이름?account=아이디, 세션 id

}