package mos.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import mos.category.dto.CreateCategoryRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

class CategoryIntegrationTest extends IntegrationTest {

    @Test
    void 카테고리_생성_테스트() throws Exception {
        // given
        CreateCategoryRequest request = new CreateCategoryRequest("신규 카테고리");
        String jsonRequest = objectMapper.writeValueAsString(request);

        Long beforeCategoryCount = entityManager.createQuery("select count(*) from Category c", Long.class)
                .getSingleResult();

        // when
        this.mockMvc.perform(post("/api/categories/create")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION));

        // then
        Long afterCategoryCount = entityManager.createQuery("select count(*) from Category c", Long.class)
                .getSingleResult();
        assertThat(afterCategoryCount).isEqualTo(beforeCategoryCount + 1);
    }
}
