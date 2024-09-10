package mos.integration.dto;

import jakarta.servlet.http.Cookie;

public record LoginResponse(String accessToken, Cookie refreshToken) {

    public String createAuthorizationHeader() {
        return "Bearer " + accessToken;
    }
}
