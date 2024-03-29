package mos.mogako.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mos.common.BaseTimeEntity;

@Entity
@Getter
@RequiredArgsConstructor
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
}
