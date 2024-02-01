package com.hkit.lifetime.survey;

import java.io.Serializable;

/**
 * DTO for {@link Survey}
 */
public record SurveyDto(Long id, String json, String account_uuid) implements Serializable {
}