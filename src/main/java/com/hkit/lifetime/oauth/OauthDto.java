package com.hkit.lifetime.oauth;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Oauth}
 */
@Value
public class OauthDto implements Serializable {
    String oauthKey;
    String uuid;
}