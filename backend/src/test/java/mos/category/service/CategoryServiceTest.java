package mos.category.service;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import mos.category.dto.CategoriesResponse;
import mos.category.dto.CreateCategoryRequest;
import mos.category.entity.Category;
import org.junit.jupiter.api.BeforeEach;
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

    private Category category1;
    private Category category2;

    @BeforeEach
    void setUp() {
        category1 = Category.createCategory("카테고리 이름1");
        category2 = Category.createCategory("카테고리 이름2");

        entityManager.persist(category1);
        entityManager.persist(category2);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void 신규_카테고리를_생성한다() {
        // given
        CreateCategoryRequest request = new CreateCategoryRequest("신규 카테고리");

        // when
        Long result = categoryService.createCategory(request);

        // then
        assertThat(result).isNotNull();
    }

    @Test
    void 전체_카테고리를_조회한다() {
        // given
        CategoriesResponse response = categoryService.findAll();

        // then
        assertThat(response.categories()).hasSize(2);
    }
}
