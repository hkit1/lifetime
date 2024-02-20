package com.hkit.lifetime.company;

import com.hkit.lifetime.account.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "company_account_list")
@NoArgsConstructor
public class CompanyAccountList {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "relationId", nullable = false)
  private String relationId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "uuid", nullable = false)
  private Account account;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "companyId", nullable = false)
  private Company company;

  public CompanyAccountList(Account account, Company company) {
    this.account = account;
    this.company = company;
  }

}
