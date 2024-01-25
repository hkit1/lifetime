package com.hkit.lifetime.company;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Company}
 */
@Value
public class CompanyDto implements Serializable {
    String name;
    String uuid;
}