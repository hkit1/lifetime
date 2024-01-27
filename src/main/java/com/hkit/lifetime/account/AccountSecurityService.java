package com.hkit.lifetime.account;

import com.hkit.lifetime.security.SecurityRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountSecurityService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        try {

            Optional<Account> accountById = accountRepository.findAccountById(id);
            if (accountById.isEmpty()) {
                return null;
            }

            Account account = accountById.get();
            AccountSecurity accountSecurity = new AccountSecurity(
                    account.getId(),
                    account.getPw()
            );

            SecurityRole role = account.getRole();
            accountSecurity.setAuthorities(Collections.singleton(new SimpleGrantedAuthority(role.name())));

            return accountSecurity;
        } catch (Exception e) {
            return null;
        }

    }

}
