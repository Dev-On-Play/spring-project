package mos.hashtag.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mos.hashtag.dto.CreateHashtagRequest;
import mos.hashtag.dto.HashtagsResponse;
import mos.hashtag.service.HashtagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Tag(name = "해시태그 관련 API")
@RestController
@RequiredArgsConstructor
public class HashtagController {

    private final HashtagService hashtagService;

    @Operation(summary = "단일 해시태그 생성")
    @PostMapping("/api/hashtags/create")
    public ResponseEntity<Void> createHashtag(@RequestBody CreateHashtagRequest request) {
        Long createdHashtagId = hashtagService.createHashtag(request);
        return ResponseEntity.created(URI.create("/api/hashtags/" + createdHashtagId)).build();
    }

    @Operation(summary = "전체 해시태그 목록 조회")
    @GetMapping("/api/hashtags")
    public ResponseEntity<HashtagsResponse> findAllHashtags() {
        return ResponseEntity.ok(hashtagService.findAll());
    }
}
