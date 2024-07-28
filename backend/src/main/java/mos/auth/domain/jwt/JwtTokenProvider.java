package mos.auth.domain.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    public String createAccessToken(String subject, Long accessTokenExpireLength, String secretKey) {
        Claims claims = generateClaims(subject, accessTokenExpireLength);

        return Jwts.builder()
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)),
                        SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims generateClaims(String subject, Long accessTokenExpireLength) {
        Date now = new Date();
        Date expiredAt = new Date(now.getTime() + accessTokenExpireLength);

        return Jwts.claims()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiredAt);
    }

    public String parseSubject(String accessToken, String secretKey) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException exception) {
            throw new IllegalArgumentException(); // todo : AccessTokenExpiredException
        } catch (JwtException exception) {
            throw new IllegalArgumentException(); // todo : InvalidAccessTokenException
        }

    }
}
