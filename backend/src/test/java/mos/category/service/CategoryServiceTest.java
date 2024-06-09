package mos.category.service;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import mos.category.dto.CreateCategoryRequest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Transactional
@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private EntityManager entityManager;

    @Test
    void 신규_카테고리를_생성한다() {
        // given
        CreateCategoryRequest request = new CreateCategoryRequest("신규 카테고리");

        // when
        Long result = categoryService.createCategory(request);

        // then
        assertThat(result).isNotNull();
    }

}
