package com.hkit.lifetime.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    public final AccountRepository accountRepository;

    public String register(AccountDto accountDto) {
        Account account = Account.toAccount(accountDto);
        return accountRepository.save(account).getId();
    }

    public void delete(String id) {
        Optional<Account> accountById = accountRepository.findAccountById(id);
        accountRepository.delete(accountById.get());
    }

    public void update(AccountDto accountDto) {
        Optional<Account> accountById = accountRepository.findAccountById(accountDto.id());

        if (accountById.isPresent()) {
            Account account = accountById.get();
            account.updateAccount(accountDto);

            accountRepository.save(account);
        }

    }

    public Optional<Account> duplicateCheck(String id) {
        return accountRepository.findAccountById(id);
    }

}
