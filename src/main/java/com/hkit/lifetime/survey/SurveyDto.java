package com.hkit.lifetime.survey;

import java.io.Serializable;

/**
 * DTO for {@link Survey}
 */
public record SurveyDto(String json, String id) implements Serializable {
}