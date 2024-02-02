package com.hkit.lifetime.lecture;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link LectureContent}
 */
public record LectureContentDto(Integer id,
                                String lecture_id,
                                String name,
                                String description,
                                String url) implements Serializable {
}