package com.hkit.lifetime.lecture;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link Lecture}
 */
public record LectureDto(Integer id, String name, LocalDate createdAt, LocalDate closedAt, Integer category,
                         String companyId) implements Serializable {
}