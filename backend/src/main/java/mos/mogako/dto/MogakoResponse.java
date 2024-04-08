package mos.mogako.dto;

import mos.mogako.entity.Mogako;

import java.time.LocalDateTime;

public record MogakoResponse(Long id, String name, String summary,
                             LocalDateTime startDate, LocalDateTime endDate,
                             Integer participantLimit, Integer participantCount,
                             Integer minimumParticipantCount, String detailContent) {

    public static MogakoResponse from (Mogako mogako) {
        return new MogakoResponse(mogako.getId(), mogako.getName(), mogako.getSummary(),
                mogako.getStartDate(), mogako.getEndDate(),
                mogako.getParticipantLimit(), mogako.getParticipantCount(),
                mogako.getMinimumParticipantCount(), mogako.getDetailContent());
    }
}
