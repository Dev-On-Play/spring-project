package mos.auth.dto;

public record OauthLoginRequest(String oauthProvider, String code) {
}
