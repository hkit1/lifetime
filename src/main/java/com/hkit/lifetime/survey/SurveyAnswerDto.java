package com.hkit.lifetime.survey;

import java.io.Serializable;
import lombok.Value;

/** DTO for {@link SurveyAnswer} */
@Value
public class SurveyAnswerDto implements Serializable {
  Integer id;
  String json;
}
