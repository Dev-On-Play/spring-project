package mos.participant.entity;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import mos.common.entity.BaseTimeEntity;
import mos.member.entity.Member;
import mos.mogako.entity.Mogako;

@Entity
@RequiredArgsConstructor
public class Participant extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Mogako mogako;

    private Boolean isHost; // 방장 여부
    private Boolean isAttended; // 실제 모각코 출석 여부

    private Participant(Member member, Mogako mogako, Boolean isHost, Boolean isAttended) {
        this.member = member;
        this.mogako = mogako;
        this.isHost = isHost;
        this.isAttended = isAttended;
    }

    public static Participant createNewParticipant(Member member, Mogako mogako, Boolean isHost) {
        Participant participant;
        if (isHost) {
            participant = new Participant(member, mogako, isHost, true);
        } else {
            participant = new Participant(member, mogako, isHost, false);
        }
        mogako.participate(participant);
        return participant;
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return member.getId();
    }

    public boolean isHost() {
        return this.isHost;
    }

    public boolean isAttended() {
        return this.isAttended;
    }
}
