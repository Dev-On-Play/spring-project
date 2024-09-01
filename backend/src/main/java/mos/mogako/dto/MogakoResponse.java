package mos.mogako.dto;

import mos.category.dto.CategoryResponse;
import mos.hashtag.dto.HashtagResponse;
import mos.mogako.entity.Mogako;

import java.time.LocalDateTime;
import java.util.List;

public record MogakoResponse(Long id, String name, String summary,
                             CategoryResponse category,
                             List<HashtagResponse> hashtags,
                             LocalDateTime startDate, LocalDateTime endDate,
                             Integer participantLimit, Integer participantCount,
                             Integer minimumParticipantCount, String detailContent) {

    public static MogakoResponse from(Mogako mogako) {
        CategoryResponse category = CategoryResponse.from(mogako.getCategory());
        List<HashtagResponse> hashtags = mogako.getHashtags().stream()
                .map(HashtagResponse::from)
                .toList();

        return new MogakoResponse(mogako.getId(), mogako.getName(), mogako.getSummary(),
                category, hashtags,
                mogako.getStartDate(), mogako.getEndDate(),
                mogako.getParticipantLimit(), mogako.getParticipantCount(),
                mogako.getMinimumParticipantCount(), mogako.getDetailContent());
    }
}
