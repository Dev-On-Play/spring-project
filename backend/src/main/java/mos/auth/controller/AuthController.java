package mos.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mos.auth.dto.OauthLoginRequest;
import mos.auth.dto.TokenResponse;
import mos.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "로그인 관련 기능")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "소셜 로그인 요청")
    @PostMapping("/api/auth/login")
    public ResponseEntity<TokenResponse> oauthLogin(@RequestBody OauthLoginRequest oauthLoginRequest) {
        TokenResponse response = authService.oauthLogin(oauthLoginRequest);
        return ResponseEntity.ok(response);
    }
}
