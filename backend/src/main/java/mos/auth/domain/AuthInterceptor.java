package mos.auth.domain;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import mos.auth.domain.jwt.JwtTokenProvider;
import mos.auth.domain.jwt.TokenValues;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final BearerAuthorizationParser bearerAuthorizationParser;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenValues tokenValues;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String accessToken = bearerAuthorizationParser.parse(request.getHeader(HttpHeaders.AUTHORIZATION));

        Long memberId = Long.parseLong(jwtTokenProvider.parseSubject(accessToken, tokenValues.secretKey()));
        request.setAttribute("memberId", memberId);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
