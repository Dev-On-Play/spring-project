package mos.auth.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GoogleOauthValues {

    @Value("${oauth.client-id}")
    public String clientId;

    @Value("${oauth.client-secret}")
    public String clientSecret;

    @Value("${oauth.redirect-uri}")
    public String redirectUri;

    @Value("${oauth.grant-type}")
    public String grantType;
}
