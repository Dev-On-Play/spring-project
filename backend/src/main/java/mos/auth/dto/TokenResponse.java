package mos.auth.dto;

import mos.auth.entity.RefreshToken;

public record TokenResponse(String AccessToken, String RefreshToken) {

    public static TokenResponse of(String accessToken, RefreshToken refreshToken) {
        return new TokenResponse(accessToken, refreshToken.getUuid().toString());
    }
}
