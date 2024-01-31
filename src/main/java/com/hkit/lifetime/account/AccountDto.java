package com.hkit.lifetime.account;

import java.time.LocalDate;

/**
 * DTO for {@link Account}
 */
public record AccountDto(String uuid, String name, String id, String pw, LocalDate birth, String email, String tel,
                         Boolean gender, String address1, String address2) {
}