package com.hkit.lifetime.oauth;

import com.hkit.lifetime.account.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "oauth")
public class Oauth {
    @Id
    @Column(name = "oauth_key", nullable = false, length = 1024)
    private String oauthKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uuid")
    private Account uuid;

}