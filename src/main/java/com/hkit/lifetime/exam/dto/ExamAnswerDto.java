package com.hkit.lifetime.exam.dto;

import com.hkit.lifetime.exam.ExamAnswer;

import java.io.Serializable;

/** DTO for {@link ExamAnswer} */
public record ExamAnswerDto(
        String json,
        String accountId
) implements Serializable {}
