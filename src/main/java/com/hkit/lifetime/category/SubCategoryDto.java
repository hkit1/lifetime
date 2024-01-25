package com.hkit.lifetime.category;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link SubCategory}
 */
@Value
public class SubCategoryDto implements Serializable {
    Integer id;
    Integer categoryId;
    String name;
}