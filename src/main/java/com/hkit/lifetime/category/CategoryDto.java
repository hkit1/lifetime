package com.hkit.lifetime.category;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Category}
 */
@Value
public class CategoryDto implements Serializable {
    Integer id;
    String name;
}