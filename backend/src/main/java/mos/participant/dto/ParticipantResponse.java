package mos.participant.dto;

import mos.participant.entity.Participant;

public record ParticipantResponse(Long memberId, boolean isHost, boolean isAttended) {

    public static ParticipantResponse from(Participant participant) {
        return new ParticipantResponse(participant.getMemberId(), participant.isHost(), participant.isAttended());
    }
}
