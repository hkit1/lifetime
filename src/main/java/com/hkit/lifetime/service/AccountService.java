package com.hkit.lifetime.service;

import com.hkit.lifetime.entity.Account;
import com.hkit.lifetime.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    public final AccountRepository accountRepository;

    public void save(Account account) {
        accountRepository.save(account);
    }

    public void delete(Account account) {
        accountRepository.delete(account);
    }
}
