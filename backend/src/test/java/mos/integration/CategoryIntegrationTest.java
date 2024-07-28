package mos.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import mos.category.dto.CategoriesResponse;
import mos.category.dto.CreateCategoryRequest;
import mos.category.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

class CategoryIntegrationTest extends IntegrationTest {

    private Category category1;
    private Category category2;
    private Long initCategoryCount;

    @BeforeEach
    void setUp() {
        category1 = Category.createCategory("카테고리 이름1");
        category2 = Category.createCategory("카테고리 이름2");

        entityManager.persist(category1);
        entityManager.persist(category2);

        entityManager.flush();
        entityManager.clear();

        initCategoryCount = entityManager.createQuery("select count(*) from Category c", Long.class)
                .getSingleResult();
    }

    @Test
    void 카테고리_생성_테스트() throws Exception {
        // given
        CreateCategoryRequest request = new CreateCategoryRequest("신규 카테고리");
        String jsonRequest = objectMapper.writeValueAsString(request);

        // when
        this.mockMvc.perform(post("/api/categories/create")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION));

        // then
        Long afterCategoryCount = entityManager.createQuery("select count(*) from Category c", Long.class)
                .getSingleResult();
        assertThat(afterCategoryCount).isEqualTo(initCategoryCount + 1);
    }

    @Test
    void 카테고리_전체_조회_테스트() throws Exception {
        // given
        MvcResult result = this.mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        CategoriesResponse response = objectMapper.readValue(json, CategoriesResponse.class);

        // then
        assertThat(response.categories()).hasSize(initCategoryCount.intValue());
    }
}
