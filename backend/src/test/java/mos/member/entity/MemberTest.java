package mos.member.entity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

class MemberTest {

    @Test
    void 멤버_생성_테스트() {
        // given, then
        assertDoesNotThrow(() -> Member.createNewMember("닉네임", "testemail@gmail.com",
                "자기소개", "프로필 사진 url", 36.5));
    }
}
