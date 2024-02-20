package com.hkit.lifetime.account;

import com.hkit.lifetime.security.SecurityRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AccountSecurityService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {


            Account findAccount = accountRepository.findAccountById(id)
                    .orElseThrow(() -> new UsernameNotFoundException("사용자가 존재하지 않습니다."));

            AccountSecurity accountSecurity = new AccountSecurity(
                    findAccount.getId(),
                    findAccount.getPw()
            );

            // todo fix
            SecurityRole role = findAccount.getRole();
            accountSecurity.setAuthorities(Collections.singleton(new SimpleGrantedAuthority(role.name())));

            return accountSecurity;
    }
}
