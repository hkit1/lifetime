package com.hkit.lifetime.exam;

import java.io.Serializable;
import lombok.Value;

/** DTO for {@link ExamAnswer} */
@Value
public class ExamAnswerDto implements Serializable {
  String id;
  String json;
}
