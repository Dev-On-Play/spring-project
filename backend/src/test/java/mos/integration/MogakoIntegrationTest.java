package mos.integration;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import mos.mogako.dto.CreateMogakoRequest;
import mos.mogako.dto.MogakoResponse;
import mos.mogako.entity.Mogako;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

class MogakoIntegrationTest extends IntegrationTest {

    private Mogako mogako1;
    private Mogako mogako2;

    @BeforeEach
    void setUp() {
        mogako1 = Mogako.createNewMogako("모각코1", "모각코 짧은 소개1",
                LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(2L),
                8, 2,
                "모각코 상세설명");
        mogako2 = Mogako.createNewMogako("모각코2", "모각코 짧은 소개2",
                LocalDateTime.now().plusDays(2L), LocalDateTime.now().plusDays(3L),
                10, 4,
                "모각코 상세설명2");

        entityManager.persist(mogako1);
        entityManager.persist(mogako2);

        System.out.println(mogako1.getId());
        System.out.println(mogako2.getId());

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

        Long beforeMogakoCount = entityManager.createQuery("select count(*) from Mogako m", Long.class)
                .getSingleResult();

        // when
        this.mockMvc.perform(post("/api/mogakos/create")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION));

        // then
        Long afterMogakoCount = entityManager.createQuery("select count(*) from Mogako m", Long.class)
                .getSingleResult();
        assertThat(afterMogakoCount).isEqualTo(beforeMogakoCount + 1);
    }

    @Test
    void 모각코_조회_테스트() throws Exception {
        // given, when
        MvcResult result = this.mockMvc.perform(get("/api/mogakos/{mogakoId}", mogako1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // then
        String json = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        MogakoResponse response = objectMapper.readValue(json, MogakoResponse.class);

        SoftAssertions.assertSoftly((softly) -> {
            softly.assertThat(response.id()).isEqualTo(mogako1.getId());
            softly.assertThat(response.name()).isEqualTo(mogako1.getName());
        });
    }
}
