package com.hkit.lifetime.company;

import com.hkit.lifetime.account.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "company_account_list")
public class CompanyAccountList {
  @Id
  @GeneratedValue
  @Column(name = "relationId", nullable = false)
  private String relationId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "account", nullable = false)
  private Account account;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "companyId", nullable = false)
  private Company company;
}
