package mos.mogako.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mos.mogako.dto.CreateMogakoRequest;
import mos.mogako.dto.MogakoResponse;
import mos.mogako.dto.MogakosResponse;
import mos.mogako.entity.Mogako;
import mos.mogako.service.MogakoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Tag(name = "모각코 관련 API")
@RestController
@RequiredArgsConstructor
public class MogakoController {

    private static Mogako tempMogako = Mogako.createNewMogako("모각코 이름", "모각코 짧은 소개",
            LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(2L),
            8, 2,
            "모각코 상세설명");

    private final MogakoService mogakoService;

    @Operation(summary = "모각코 일정 전체 조회")
    @GetMapping("/api/mogakos")
    public ResponseEntity<MogakosResponse> findMogakos(Pageable pageable) {
        MogakosResponse mogakosResponse = new MogakosResponse(10, new ArrayList<>());
        return ResponseEntity.ok(mogakosResponse);
    }

    @Operation(summary = "단일 모각코 상세 정보 조회")
    @GetMapping("/api/mogakos/{mogakoId}")
    public ResponseEntity<MogakoResponse> findMogako(@PathVariable Long mogakoId) {
        MogakoResponse response = MogakoResponse.from(tempMogako);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "단일 모각코 정보 수정")
    @PutMapping("/api/mogakos/{mogakoId}")
    public ResponseEntity<MogakoResponse> modifyMogako(@PathVariable Long mogakoId) {
        MogakoResponse mogakoResponse = MogakoResponse.from(tempMogako);
        // 수정한 모각코 정보 반환
        return ResponseEntity.ok(mogakoResponse);
    }

    @Operation(summary = "신규 모각코 생성")
    @PostMapping("/api/mogakos/create")
    public ResponseEntity<Void> createMogako(@RequestBody CreateMogakoRequest request) {
        Long createdMogakoId = mogakoService.createMogako(request);
        return ResponseEntity.created(URI.create("api/mogakos/" + createdMogakoId)).build();
    }
}
