package com.hkit.lifetime.account;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        Account findAccount = accountRepository.findAccountById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account Not Found"));
        accountRepository.delete(findAccount);
    }

    public void update(AccountDto accountDto) {
        Account findAccount = accountRepository.findAccountById(accountDto.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account Not Found"));

        findAccount.updateAccount(accountDto);
        accountRepository.save(findAccount);
    }

    public Boolean duplicateCheck(String id) {
        Account findAccount = accountRepository.findAccountById(id).orElseGet(Account::new);
        return findAccount.getId() == null;
    }

    public Optional<Account> findAccount(String id, String pw) {
        return accountRepository.findByIdAndPw(id, pw);
    }
}
