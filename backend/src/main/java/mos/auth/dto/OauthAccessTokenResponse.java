package mos.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OauthAccessTokenResponse(
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("access_token") String accessToken,
        String scope) {
}
