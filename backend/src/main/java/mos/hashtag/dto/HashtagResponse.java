package mos.hashtag.dto;

import mos.hashtag.entity.Hashtag;

public record HashtagResponse(Long id, String name) {
    public static HashtagResponse from(Hashtag hashtag) {
        return new HashtagResponse(hashtag.getId(), hashtag.getName());
    }
}
