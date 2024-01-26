package com.hkit.lifetime.account;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Account}
 */
public record AccountDto(UUID uuid, String id, String pw, String birth, String email, String tel, Integer gender, String address1, String address2) implements Serializable {
}