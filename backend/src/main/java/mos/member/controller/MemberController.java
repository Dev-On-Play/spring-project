package mos.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mos.auth.domain.Authenticated;
import mos.member.dto.MemberResponse;
import mos.member.dto.UpdateMemberRequest;
import mos.member.service.MemberService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "멤버 관련 API")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "단일 멤버정보 조회")
    @GetMapping("/api/members/{memberId}")
    public ResponseEntity<MemberResponse> findMember(@Authenticated Long authMemberId, @PathVariable Long memberId) {
        MemberResponse response = memberService.findMember(memberId);
        return ResponseEntity.ok(response);
    }

    // todo : 서비스 확장 과정에서 외부로 노출되어선 안되는 멤버 정보가 생기면 단일 멤버 정보 조회 API와 구분할 필요가 있어짐.
    @Operation(summary = "나의 멤버정보 조회")
    @GetMapping("/api/members/me")
    public ResponseEntity<MemberResponse> findMe(@Authenticated Long authMemberId) {
        MemberResponse response = memberService.findMember(authMemberId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "나의 멤버정보 수정")
    @PutMapping("/api/members/me")
    public ResponseEntity<Void> updateMe(@Authenticated Long authMemberId, @RequestBody UpdateMemberRequest request) {
        Long updatedMemberId = memberService.updateMember(authMemberId, request);
        return ResponseEntity.noContent()
                .header(HttpHeaders.LOCATION, "api/members/" + updatedMemberId)
                .build();
    }
}
