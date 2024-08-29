package mos.hashtag.dto;

import java.util.List;

public record HashtagsResponse(List<HashtagResponse> hashtags) {

    public static HashtagsResponse from(List<HashtagResponse> responses) {
        return new HashtagsResponse(responses);
    }
}
