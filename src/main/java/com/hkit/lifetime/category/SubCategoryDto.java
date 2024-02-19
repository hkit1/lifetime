package com.hkit.lifetime.category;

import java.io.Serializable;

/**
 * DTO for {@link SubCategory}
 */
public record SubCategoryDto(Integer id, Integer categoryId, String name) implements Serializable {
}