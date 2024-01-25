package com.hkit.lifetime.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "o_auth")
public class OAuth {
    @Id
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "account_uuid")
    private Account account;
}