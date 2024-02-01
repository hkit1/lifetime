package com.hkit.lifetime.account;

import com.hkit.lifetime.security.SecurityRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.management.relation.Role;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountSecurity implements UserDetails {

    private String id;
    private String pw;
    private Collection<? extends GrantedAuthority> authorities;

    public AccountSecurity(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return pw;
    }

    @Override
    public String getUsername() {
        return id;
    }

    /*
        계정 만료 여부
        true : 만료 x
        false : 만료 o
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /*
        계정 잠김 여부
        true : 잠김 x
        false : 잠김 o
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /*
        비밀번호 만료 여부
        true : 만료 x
        false : 만료 o
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /*
        사용자 활성화 여부
        ture : 활성화
        false : 비활성화
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
