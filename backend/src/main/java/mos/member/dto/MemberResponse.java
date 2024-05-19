package mos.member.dto;

import mos.member.entity.Member;

public record MemberResponse(Long id, String nickname,
                             String introduction, String profile, Double credibility) {

    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getId(), member.getNickname(),
                member.getIntroduction(), member.getProfile(), member.getCredibility());
    }
}
