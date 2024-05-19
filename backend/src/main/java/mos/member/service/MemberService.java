package mos.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mos.member.dto.MemberResponse;
import mos.member.entity.Member;
import mos.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponse findMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버 Id 입니다."));
        return MemberResponse.from(member);
    }
}
