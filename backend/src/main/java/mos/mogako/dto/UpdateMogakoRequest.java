package mos.mogako.dto;

import java.time.LocalDateTime;
import java.util.List;

public record UpdateMogakoRequest(
        String name, String summary,
        Long categoryId, List<Long> hashtagIds,
        LocalDateTime startDate, LocalDateTime endDate,
        int participantLimit, int minimumParticipantCount,
        String detailContent
) {
}
