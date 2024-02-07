package com.hkit.lifetime.lecture;

import java.io.Serializable;

/**
 * DTO for {@link LectureContent}
 */
public record LectureContentDto(Integer id,
                                Integer lecture_id,
                                String name,
                                String description,
                                String url) implements Serializable {
}