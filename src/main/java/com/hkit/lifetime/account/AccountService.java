package com.hkit.lifetime.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    public final AccountRepository accountRepository;

    public void save(AccountDto accountDto) {
        //아이디 중복 검사
        Optional<Account> accountById = accountRepository.findAccountById(accountDto.id());
        if (accountById.isEmpty()) {
            Account account = Account.toAccount(accountDto);
            accountRepository.save(account);
        }
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
}
