package com.hkit.lifetime.rating;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Rating}
 */
public record RatingDto(Integer id, Integer lectureId, String uuid) implements Serializable {
}