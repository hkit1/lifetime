package com.hkit.lifetime.exam;


import java.io.Serializable;
import java.time.LocalDate;

/** DTO for {@link Exam} */
public record ExamDto(
    String examId,
    Integer lectureId,
    String lectureName,
    LocalDate lectureCreatedAt,
    LocalDate lectureClosedAt,
    LocalDate createdAt,
    LocalDate updatedAt,
    String json)
    implements Serializable {}
