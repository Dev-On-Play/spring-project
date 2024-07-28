package mos.auth.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mos.auth.domain.jwt.JwtTokenProvider;
import mos.auth.domain.jwt.TokenValues;
import mos.auth.dto.OauthUserProfileResponse;
import mos.auth.dto.TokenResponse;
import mos.auth.entity.RefreshToken;
import mos.auth.repository.RefreshTokenRepository;
import mos.member.entity.Member;
import mos.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthTransactionalService {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenValues tokenValues;

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenResponse userLogin(OauthUserProfileResponse response) {
        // 멤버 정보가 기존에 존재했으면 업데이트, 아니면 새로 생성
        Member member = createOrUpdateMember(response);
        return publishAccessAndRefreshToken(member);
    }

    private Member createOrUpdateMember(OauthUserProfileResponse response) {
        Member member = memberRepository.findByNicknameAndEmail(response.name(), response.email())
                .orElseGet(() -> Member.createNewMember(response.name(), response.email(), response.profileImageUri()));
        member.updateProfileImageUri(response.profileImageUri());
        return memberRepository.save(member);
    }

    private TokenResponse publishAccessAndRefreshToken(Member member) {
        // jwt accessToken, refreshToken 신규 발급 후 반환
        String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(member.getId()),
                tokenValues.accessTokenExpireLength(), tokenValues.secretKey());
        RefreshToken refreshToken = refreshTokenRepository.findByMember(member)
                .orElseGet(() -> RefreshToken.of(member, tokenValues.refreshTokenExpireLength()));

        while (refreshTokenRepository.existsByUuid(refreshToken.getUuid())) {
            refreshToken.updateUuidAndExpireDateTime(tokenValues.refreshTokenExpireLength());
        }
        refreshTokenRepository.save(refreshToken);

        return TokenResponse.of(accessToken, refreshToken);
    }

    public TokenResponse republishAccessAndRefreshToken(String inputRefreshToken) {
        // jwt accessToken, refreshToken 재발급 후 반환
        RefreshToken refreshToken = refreshTokenRepository.findByUuid(UUID.fromString(inputRefreshToken))
                .orElseThrow(IllegalArgumentException::new);    // todo : InvalidRefreshTokenException
        refreshToken.validateExpired();
        refreshToken.updateUuidAndExpireDateTime(tokenValues.refreshTokenExpireLength());

        String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(refreshToken.getMember().getId()),
                tokenValues.accessTokenExpireLength(), tokenValues.secretKey());

        return TokenResponse.of(accessToken, refreshToken);
    }

    public void deleteRefreshToken(String inputRefreshToken) {
        RefreshToken refreshToken = refreshTokenRepository.findByUuid(UUID.fromString(inputRefreshToken))
                .orElseThrow(IllegalArgumentException::new);// todo : InvalidLogoutRequestException
        refreshTokenRepository.delete(refreshToken);
    }
}
