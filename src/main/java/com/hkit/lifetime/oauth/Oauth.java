package com.hkit.lifetime.oauth;

import com.hkit.lifetime.account.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "oauth")
public class Oauth implements OAuth2User {
    @Id
    @Column(name = "oauth_key", nullable = false, length = 1024)
    private String oauthKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uuid")
    private Account uuid;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}