package mos.auth.domain;

import lombok.RequiredArgsConstructor;
import mos.auth.dto.OauthAccessTokenResponse;
import mos.auth.dto.OauthUserProfileResponse;
import mos.auth.exception.OauthServerException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GoogleOauthWebClient {

    private static final String REQUEST_TOKEN_URI = "https://oauth2.googleapis.com/token";
    private static final String REQUEST_USER_PROFILE_URI = "https://www.googleapis.com/oauth2/v3/userinfo";

    private final GoogleOauthValues googleOauthValues;

    public OauthAccessTokenResponse requestOauthAccessToken(String code) {
        return WebClient.create()
                .post()
                .uri(REQUEST_TOKEN_URI)
                .headers(header -> {
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAccept(List.of(MediaType.APPLICATION_JSON));
                    header.setAcceptCharset(List.of(StandardCharsets.UTF_8));
                })
                .bodyValue(createRequestAccessTokenBody(code))
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(String.class)
                        .map(OauthServerException::new))
                .bodyToMono(OauthAccessTokenResponse.class)
                .block();
    }

    private MultiValueMap<String, String> createRequestAccessTokenBody(String code) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code);
        body.add("client_id", googleOauthValues.clientId);
        body.add("client_secret", googleOauthValues.clientSecret);
        body.add("redirect_uri", googleOauthValues.redirectUri);
        body.add("grant_type", googleOauthValues.grantType);
        return body;
    }

    public OauthUserProfileResponse requestOauthUserProfile(String accessToken) {
        return WebClient.create()
                .post()
                .uri(REQUEST_USER_PROFILE_URI)
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(String.class)
                        .map(OauthServerException::new))
                .bodyToMono(OauthUserProfileResponse.class)
                .block();
    }
}
