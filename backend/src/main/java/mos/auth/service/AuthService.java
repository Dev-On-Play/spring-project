package mos.auth.service;

import lombok.RequiredArgsConstructor;
import mos.auth.domain.GoogleOauthWebClient;
import mos.auth.dto.OauthAccessTokenResponse;
import mos.auth.dto.OauthLoginRequest;
import mos.auth.dto.OauthUserProfileResponse;
import mos.auth.dto.TokenResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final GoogleOauthWebClient googleOauthWebClient;

    private final AuthTransactionalService authTransactionalService;

    public TokenResponse oauthLogin(OauthLoginRequest request) {
        // client로부터 전달받은 code를 이용해 oauth accessToken을 구글 서버에 요청
        OauthAccessTokenResponse response = googleOauthWebClient.requestOauthAccessToken(request.code());

        // 그 뒤 oauth accessToken을 사용해서 사용자 프로필 정보 요청해서 가져오기
        OauthUserProfileResponse profileResponse = googleOauthWebClient.requestOauthUserProfile(response.accessToken());

        // 사용자 프로필 정보를 이용해서 로그인 프로세스 진행 후 access, refresh Token 발급
        return authTransactionalService.userLogin(profileResponse);
    }

    public TokenResponse refresh(String refreshToken) {
        return authTransactionalService.republishAccessAndRefreshToken(refreshToken);
    }

    public void oauthLogout(String refreshToken) {
        authTransactionalService.deleteRefreshToken(refreshToken);
    }
}
