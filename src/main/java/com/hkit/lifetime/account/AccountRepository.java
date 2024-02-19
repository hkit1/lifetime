package com.hkit.lifetime.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findAccountById(String id);

    Optional<Account> findAccountByName(String name);

    Optional<Account> findByIdAndPw(String id, String pw);

    @Query("select count(a) from Account a where a.created_at > ?1")
    long countByCreated_atAfter(LocalDate date);
}