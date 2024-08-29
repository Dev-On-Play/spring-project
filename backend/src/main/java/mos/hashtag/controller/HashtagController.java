package mos.hashtag.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import mos.hashtag.dto.CreateHashtagRequest;
import mos.hashtag.service.HashtagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

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
}
