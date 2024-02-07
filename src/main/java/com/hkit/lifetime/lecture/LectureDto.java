package com.hkit.lifetime.lecture;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link Lecture}
 */
public record LectureDto(Integer id,
                         String name,
                         String created_at,
                         String closed_at,
                         String sub_category,
                         String company_name,
                         MultipartFile file) implements Serializable {
}