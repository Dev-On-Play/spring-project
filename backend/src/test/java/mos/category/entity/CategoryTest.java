package mos.category.entity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

class CategoryTest {

    @Test
    void 카테고리_생성_테스트() {
        // given, then
        assertDoesNotThrow(() -> Category.createCategory("테스트 카테고리"));
    }
}
