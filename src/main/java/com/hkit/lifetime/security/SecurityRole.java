package com.hkit.lifetime.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SecurityRole {

    USER("USER"),
    ADMIN("ADMIN");

    private String value;

}
