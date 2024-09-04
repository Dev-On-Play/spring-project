package mos.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import mos.auth.domain.jwt.TokenValues;
import mos.auth.dto.OauthLoginRequest;
import mos.auth.dto.TokenResponse;
import mos.auth.exception.RefreshTokenNotExistsInCookieException;
import mos.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@Tag(name = "로그인 관련 기능")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final TokenValues tokenValues;

    @Operation(summary = "소셜 로그인 요청")
    @PostMapping("/api/auth/login")
    public ResponseEntity<TokenResponse> oauthLogin(HttpServletResponse response, @RequestBody OauthLoginRequest oauthLoginRequest) {
        TokenResponse tokenResponse = authService.oauthLogin(oauthLoginRequest);
        Cookie cookie = setUpRefreshTokenCookie(tokenResponse);
        response.addCookie(cookie);
        return ResponseEntity.ok(tokenResponse);
    }

    @Operation(summary = "access 토큰, refresh 토큰 갱신")
    @PostMapping("/api/auth/refresh")
    public ResponseEntity<TokenResponse> refresh(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String refreshToken = extractRefreshTokenFromCookie(request);
        TokenResponse tokenResponse = authService.refresh(refreshToken);
        Cookie cookie = setUpRefreshTokenCookie(tokenResponse);
        response.addCookie(cookie);
        return ResponseEntity.ok(tokenResponse);
    }

    private String extractRefreshTokenFromCookie(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("refreshToken"))
                .map(Cookie::getValue)
                .findAny()
                .orElseThrow(RefreshTokenNotExistsInCookieException::new);
    }

    private Cookie setUpRefreshTokenCookie(TokenResponse tokenResponse) {
        Cookie cookie = new Cookie("refreshToken", tokenResponse.refreshToken());
        cookie.setMaxAge((int) (tokenValues.refreshTokenExpireLength() / 1000L));
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        return cookie;
    }

    @Operation(summary = "로그아웃, access & refresh 토큰 삭제")
    @PostMapping("/api/auth/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = extractRefreshTokenFromCookie(request);
        authService.oauthLogout(refreshToken);
        deleteRefreshTokenFromCookie(request, response);

        return ResponseEntity.ok().build();
    }

    private void deleteRefreshTokenFromCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            deleteRefreshTokenIfPresent(response, cookies);
        }
    }

    private void deleteRefreshTokenIfPresent(HttpServletResponse response, Cookie[] cookies) {
        Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("refreshToken"))
                .findFirst()
                .ifPresent(cookie -> deleteCookie(response, cookie));
    }

    private void deleteCookie(HttpServletResponse response, Cookie cookie) {
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
