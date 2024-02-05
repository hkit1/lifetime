package com.hkit.lifetime.exam.dto;

import java.io.Serializable;

import com.hkit.lifetime.exam.ExamAnswer;
import lombok.Value;

/** DTO for {@link ExamAnswer} */
public record ExamAnswerDto(
        String json,
        String accountId
) implements Serializable {}
