package mos.participant.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import mos.member.entity.Member;
import mos.member.exception.MemberNotFoundException;
import mos.member.repository.MemberRepository;
import mos.mogako.entity.Mogako;
import mos.mogako.exception.MogakoNotFoundException;
import mos.mogako.repository.MogakoRepository;
import mos.participant.dto.ParticipantResponse;
import mos.participant.entity.Participant;
import mos.participant.repository.ParticipantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ParticipantService {

    private final MemberRepository memberRepository;
    private final MogakoRepository mogakoRepository;
    private final ParticipantRepository participantRepository;
    private final EntityManager entityManager;

    public Long participate(Long authMemberId, Long mogakoId) {
        Member member = memberRepository.findById(authMemberId)
                .orElseThrow(MemberNotFoundException::new);
        Mogako mogako = mogakoRepository.findByIdWithOptimisticLock(mogakoId)
                .orElseThrow(MogakoNotFoundException::new);

        Participant participant = Participant.createNewParticipant(member, mogako, false);
        participantRepository.save(participant);

        return participant.getId();
    }

    public List<ParticipantResponse> findParticipants(Long mogakoId) {
        List<Participant> participants = participantRepository.findByMogakoId(mogakoId);
        return participants.stream()
                .map(ParticipantResponse::from)
                .toList();
    }
}
