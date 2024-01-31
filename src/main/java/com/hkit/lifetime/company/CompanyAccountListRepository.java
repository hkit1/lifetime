package com.hkit.lifetime.company;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyAccountListRepository extends JpaRepository<CompanyAccountList, String> {
  Optional<CompanyAccountList> findByAccount_Id(String id);
}
