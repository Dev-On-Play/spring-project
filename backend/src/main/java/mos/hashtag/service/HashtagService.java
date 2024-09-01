package mos.hashtag.service;

import lombok.RequiredArgsConstructor;
import mos.hashtag.dto.CreateHashtagRequest;
import mos.hashtag.dto.HashtagResponse;
import mos.hashtag.dto.HashtagsResponse;
import mos.hashtag.entity.Hashtag;
import mos.hashtag.repository.HashtagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HashtagService {

    private final HashtagRepository hashtagRepository;

    public Long createHashtag(CreateHashtagRequest request) {
        Hashtag hashtag = Hashtag.createNewHashtag(request.name());
        hashtagRepository.save(hashtag);
        return hashtag.getId();
    }

    public HashtagsResponse findAll() {
        List<Hashtag> hashtags = hashtagRepository.findAll();
        List<HashtagResponse> responses = hashtags.stream()
                .map(HashtagResponse::from)
                .toList();
        return HashtagsResponse.from(responses);
    }
}
