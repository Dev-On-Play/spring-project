package mos.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mos.auth.entity.RefreshToken;

public record TokenResponse(String accessToken, @JsonIgnore String refreshToken) {

    public static TokenResponse of(String accessToken, RefreshToken refreshToken) {
        return new TokenResponse(accessToken, refreshToken.getUuid().toString());
    }
}
