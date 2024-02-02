package com.hkit.lifetime.survey;

import java.io.Serializable;
import lombok.Value;

/** DTO for {@link SurveyAnswer} */
public record SurveyAnswerDto(String json) implements Serializable {
}
