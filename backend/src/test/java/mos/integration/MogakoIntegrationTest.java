package mos.integration;


import static org.assertj.core.api.Assertions.assertThat;

import mos.mogako.dto.CreateMogakoRequest;
import mos.mogako.entity.Mogako;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

class MogakoIntegrationTest extends IntegrationTest {

    @BeforeEach
    void setUp() {
        Mogako mogako1 = Mogako.createNewMogako("모각코1", "모각코 짧은 소개1",
                LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(2L),
                8, 2,
                "모각코 상세설명");
        Mogako mogako2 = Mogako.createNewMogako("모각코2", "모각코 짧은 소개2",
                LocalDateTime.now().plusDays(2L), LocalDateTime.now().plusDays(3L),
                10, 4,
                "모각코 상세설명2");

        entityManager.persist(mogako1);
        entityManager.persist(mogako2);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void 모각코_생성_테스트() throws Exception {
        // given
        CreateMogakoRequest request = new CreateMogakoRequest(1L, "새 모각코", "모각코 짧은 소개",
                LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(2L),
                8, 2,
                "모각코 상세설명");
        String jsonRequest = objectMapper.writeValueAsString(request);

        Long beforeMogakoCount = entityManager.createQuery("select count(m.id) from Mogako m", Long.class)
                .getSingleResult();

        // when
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/mogakos/create")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists(HttpHeaders.LOCATION));

        // then
        Long afterMogakoCount = entityManager.createQuery("select count(m.id) from Mogako m", Long.class)
                .getSingleResult();
        assertThat(afterMogakoCount).isEqualTo(beforeMogakoCount + 1);
    }
}
