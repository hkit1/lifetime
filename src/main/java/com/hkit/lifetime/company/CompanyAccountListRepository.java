package com.hkit.lifetime.company;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyAccountListRepository extends JpaRepository<CompanyAccountList, String> {

  @Query("select cal from CompanyAccountList cal where cal.account.id = :id")
  Optional<CompanyAccountList> findByAccount_Id(@Param("id") String id);

  @Query("select cal from CompanyAccountList cal where cal.company.id = :id")
  List<CompanyAccountList> findByCompany_Id(@Param("id") String id);
}
