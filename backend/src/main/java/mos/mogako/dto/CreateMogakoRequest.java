package mos.mogako.dto;

import java.time.LocalDateTime;

public record CreateMogakoRequest(
        String name, String summary, Long categoryId,
        LocalDateTime startDate, LocalDateTime endDate,
        int participantLimit, int minimumParticipantCount,
        String detailContent
) {
}
