package com.hkit.lifetime.category;

import java.io.Serializable;

/**
 * DTO for {@link Category}
 */
public record CategoryDto(Integer id, String name) implements Serializable {
}