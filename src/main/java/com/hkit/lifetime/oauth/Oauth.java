package com.hkit.lifetime.oauth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(name = "uuid", length = 36)
    private String uuid;

}