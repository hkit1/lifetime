package com.hkit.lifetime.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {
    Optional<Company> findByName(String name);

    @Query("select c from Company c where c.id = :id")
    Optional<Company> findById(@Param("id") String id);

    long countByAuthorizedDate(LocalDate authorizedDate);
}