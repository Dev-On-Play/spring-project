package mos.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OauthUserProfileResponse(
        String name,
        String email,
        @JsonProperty("picture") String profileImageUri
) {
}
