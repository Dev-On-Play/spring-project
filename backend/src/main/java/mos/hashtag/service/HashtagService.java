package mos.hashtag.service;

import lombok.RequiredArgsConstructor;
import mos.hashtag.dto.CreateHashtagRequest;
import mos.hashtag.entity.Hashtag;
import mos.hashtag.repository.HashtagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
