package com.hkit.lifetime.lecture;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link Lecture}
 */
public record LectureDto(Integer lectureId, String name, LocalDate createdAt, LocalDate closedAt, String companyName,
                         String uuid, Integer category) implements Serializable {
}