package mos.mogako.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class MogakoTest {

    @Test
    void 모각코_기본_생성_성공_테스트() {
        // given, then
        assertDoesNotThrow(() -> Mogako.createNewMogako("모각코 이름", "모각코 짧은 소개",
                LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(2L),
                8, 2,
                "모각코 상세설명"));
    }
}