package com.hkit.lifetime.lecture;

import jakarta.annotation.Nullable;

import java.io.Serializable;

/**
 * DTO for {@link LectureContent}
 */
public record LectureContentDto(Integer id,
                                Integer lecture_id,
                                String name,
                                String description,
                                @Nullable String url
) implements Serializable {
}