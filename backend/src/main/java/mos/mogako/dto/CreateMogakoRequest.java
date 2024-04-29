package mos.mogako.dto;

import java.time.LocalDateTime;

public record CreateMogakoRequest(
        Long categoryId,
        String name, String summary,
        LocalDateTime startDate, LocalDateTime endDate,
        int participantLimit, int minimumParticipantCount,
        String detailContent
) {
}
