package com.hkit.lifetime.company;

import java.io.Serializable;

/**
 * DTO for {@link Company}
 */
public record CompanyDto(String name) implements Serializable {
}