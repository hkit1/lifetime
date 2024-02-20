package com.hkit.lifetime.exam.dto;


import com.hkit.lifetime.exam.Exam;

import java.io.Serializable;
import java.time.LocalDate;

/** DTO for {@link Exam} */
public record ExamDto(
    Integer lectureId,
    String lectureName,
    LocalDate lectureCreatedAt,
    LocalDate lectureClosedAt,
    LocalDate createdAt,
    LocalDate updatedAt,
    String json)
    implements Serializable {}
