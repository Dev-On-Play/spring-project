package mos.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mos.member.dto.MemberResponse;
import mos.member.dto.UpdateMemberRequest;
import mos.member.entity.Member;
import mos.member.service.MemberService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "멤버 관련 API")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final Member dummyMember = Member.createNewMember("닉네임1", "test1@gmail.com", "소개글1",
            "프로필url1", 36.5);

    @Operation(summary = "단일 멤버정보 조회")
    @GetMapping("/api/members/{memberId}")
    public ResponseEntity<MemberResponse> findMember(@PathVariable Long memberId) {
        MemberResponse response = memberService.findMember(memberId);
        return ResponseEntity.ok(response);
    }

    // todo : 로그인 기능 구현 이후 인가 관련 코드 추가하며 구현 예정
    @Operation(summary = "나의 멤버정보 조회")
    @GetMapping("/api/members/me")
    public ResponseEntity<MemberResponse> findMe() {
        MemberResponse response = MemberResponse.from(dummyMember);
        return ResponseEntity.ok(response);
    }

    // todo : 로그인 기능 구현 이후 인가 관련 코드 추가하며 구현 예정
    @Operation(summary = "나의 멤버정보 수정")
    @PutMapping("api/members/me")
    public ResponseEntity<Void> updateMe(@RequestBody UpdateMemberRequest request) {
        Long updatedMemberId = 1L;
        return ResponseEntity.noContent()
                .header(HttpHeaders.LOCATION, "api/mogakos/" + updatedMemberId)
                .build();
    }
}
