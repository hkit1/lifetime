package com.hkit.lifetime.company;

import java.io.Serializable;

/**
 * DTO for {@link Company}
 */
public record CompanyDto(String companyId, String id, String name, String admin, String email, String address1,
                         String address2, String tel) implements Serializable {
}