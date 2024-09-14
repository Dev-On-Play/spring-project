package mos.mogako.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mos.auth.domain.Authenticated;
import mos.mogako.dto.CreateMogakoRequest;
import mos.mogako.dto.MogakoResponse;
import mos.mogako.dto.MogakosResponse;
import mos.mogako.dto.UpdateMogakoRequest;
import mos.mogako.service.MogakoService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "모각코 관련 API")
@RestController
@RequiredArgsConstructor
public class MogakoController {

    private final MogakoService mogakoService;

    @Operation(summary = "전체 모각코 목록 조회")
    @GetMapping("/api/mogakos")
    public ResponseEntity<MogakosResponse> findMogakos(
            @RequestParam(required = false, name = "category") List<Long> categoryIds,
            @RequestParam(required = false, name = "hashtag") List<Long> hashtagIds, Pageable pageable) {
        return ResponseEntity.ok(mogakoService.findAllWithFiltering(categoryIds, hashtagIds, pageable));
    }

    @Operation(summary = "단일 모각코 상세정보 조회")
    @GetMapping("/api/mogakos/{mogakoId}")
    public ResponseEntity<MogakoResponse> findMogako(@PathVariable Long mogakoId) {
        MogakoResponse response = mogakoService.findMogako(mogakoId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "단일 모각코 정보 수정")
    @PutMapping("/api/mogakos/{mogakoId}")
    public ResponseEntity<Void> updateMogako(@PathVariable Long mogakoId, @RequestBody UpdateMogakoRequest request) {
        Long updatedMogakoId = mogakoService.updateMogako(mogakoId, request);
        return ResponseEntity.noContent()
                .header(HttpHeaders.LOCATION, "api/mogakos/" + updatedMogakoId)
                .build();
    }

    @Operation(summary = "신규 모각코 생성")
    @PostMapping("/api/mogakos/create")
    public ResponseEntity<Void> createMogako(@Authenticated Long authMemberId, @RequestBody CreateMogakoRequest request) {
        Long createdMogakoId = mogakoService.createMogako(authMemberId, request);
        return ResponseEntity.created(URI.create("api/mogakos/" + createdMogakoId)).build();
    }
}
