package com.hkit.lifetime.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SecurityRole {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private String value;

}
