package mos.auth.dto;

import mos.auth.entity.RefreshToken;

public record TokenResponse(String accessToken, String refreshToken) {

    public static TokenResponse of(String accessToken, RefreshToken refreshToken) {
        return new TokenResponse(accessToken, refreshToken.getUuid().toString());
    }
}
