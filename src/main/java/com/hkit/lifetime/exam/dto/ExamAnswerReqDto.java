package com.hkit.lifetime.exam.dto;

import java.io.Serializable;

public record ExamAnswerReqDto(Integer lectureId,
                               Integer examId,
                               Integer answerId,
                               String accountId,
                               String json
                               ) implements Serializable {}
