package com.hkit.lifetime.lecture;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link LectureContent}
 */
public record LectureContentDto(Integer id, Integer lectureId, String lectureName, LocalDate lectureCreatedAt,
                                LocalDate lectureClosedAt, String name, String description, String url) implements Serializable {
}