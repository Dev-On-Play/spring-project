package mos.mogako.entity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import mos.category.entity.Category;
import mos.hashtag.entity.Hashtag;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class MogakoTest {

    @Test
    void 모각코_기본_생성_성공_테스트() {
        // given
        Category category = Category.createCategory("카테고리 이름");
        List<Hashtag> hashtags = new ArrayList<>();

        // then
        assertDoesNotThrow(() -> Mogako.createNewMogako("모각코 이름", "모각코 짧은 소개", category, hashtags,
                LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(2L),
                8, 2,
                "모각코 상세설명"));
    }
}
