package mos.mogako.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mos.common.BaseTimeEntity;

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
