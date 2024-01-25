package com.hkit.lifetime.lecture;

import java.io.Serializable;

/**
 * DTO for {@link LectureContent}
 */
public record LectureContentDto(Integer lecture_idLectureId, String name, String description,
                                String url) implements Serializable {
}