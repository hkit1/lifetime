package com.hkit.lifetime.account;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.hkit.lifetime.security.SecurityRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

            // todo fix
            SecurityRole role = account.getRole();
            accountSecurity.setAuthorities(Collections.singleton(new SimpleGrantedAuthority(role.name())));

            return accountSecurity;
        } catch (Exception e) {
            return null;
        }

    }

//    private List<SimpleGrantedAuthority> getRoles(List<SecurityRole> role){
//        return role.stream()
//                .map(SecurityRole::name)
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//    }

}
