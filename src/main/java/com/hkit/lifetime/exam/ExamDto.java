package com.hkit.lifetime.exam;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Exam}
 */
@Value
public class ExamDto implements Serializable {
    String uuid;
    Integer lectureId;
}