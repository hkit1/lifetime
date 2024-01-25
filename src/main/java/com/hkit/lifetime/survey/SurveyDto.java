package com.hkit.lifetime.survey;

import java.io.Serializable;

/**
 * DTO for {@link Survey}
 */
public record SurveyDto(Long id, String content, String account_uuid) implements Serializable {
}