package mos.mogako.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mos.common.BaseTimeEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mogako extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String summary;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer participantLimit;
    private Integer participantCount;
    private Integer minimumParticipantCount;
    private String detailContent;

    // todo : 카테고리 엔티티 작업 완료되면 엔티티 구조 변경 및 연관관계 추가 + 테스트 수정

    @Enumerated(EnumType.STRING)
    private Status status;

    private Mogako(String name, String summary,
                   LocalDateTime startDate, LocalDateTime endDate,
                   Integer participantLimit, Integer participantCount, Integer minimumParticipantCount,
                   String detailContent, Status status) {
        this.name = name;
        this.summary = summary;
        this.startDate = startDate;
        this.endDate = endDate;
        this.participantLimit = participantLimit;
        this.participantCount = participantCount;
        this.minimumParticipantCount = minimumParticipantCount;
        this.detailContent = detailContent;
        this.status = status;
    }

    public static Mogako createNewMogako(String name, String summary,
                                         LocalDateTime startDate, LocalDateTime endDate,
                                         Integer participantLimit, Integer minimumParticipantCount,
                                         String detailContent) {
        // todo : 모각코장이 모각코를 만들면 자기 자신이 자동으로 참여처리되도록 구현 필요.
        return new Mogako(name, summary,
                startDate, endDate,
                participantLimit, 1, minimumParticipantCount,
                detailContent, Status.RECRUITING);
    }
}
