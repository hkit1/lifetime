package com.hkit.lifetime.oauth;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Oauth}
 */
public record OauthDto(String oauthKey, String uuid) implements Serializable {
}