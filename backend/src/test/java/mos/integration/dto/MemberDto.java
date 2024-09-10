package mos.integration.dto;

public record MemberDto(Long memberId, LoginResponse loginResponse) {

    public String createAuthorizationHeader() {
        return loginResponse().createAuthorizationHeader();
    }
}
