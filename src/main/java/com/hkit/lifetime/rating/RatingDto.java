package com.hkit.lifetime.rating;

import java.io.Serializable;

/**
 * DTO for {@link Rating}
 */
public record RatingDto(Integer id, Integer lectureId, String name, Integer star, String text) implements Serializable {
}