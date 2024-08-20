package mos.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mos.member.dto.MemberResponse;
import mos.member.dto.UpdateMemberRequest;
import mos.member.entity.Member;
import mos.member.exception.MemberNotFoundException;
import mos.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponse findMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        return MemberResponse.from(member);
    }

    public Long updateMember(Long memberId, UpdateMemberRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        member.update(request.nickname(), request.introduction(), request.profile());
        return member.getId();
    }
}
