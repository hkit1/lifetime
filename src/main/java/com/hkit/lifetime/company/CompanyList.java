package com.hkit.lifetime.company;

import com.hkit.lifetime.account.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "company_list")
public class CompanyList {
  @Id
  @Column(name = "relationId", nullable = false)
  private String relationId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "uuid")
  private Account uuid;

  // TODO [JPA Buddy] generate columns from DB
}
