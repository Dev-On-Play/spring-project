package mos.integration;

import mos.member.dto.MemberResponse;
import mos.member.entity.Member;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

class MemberIntegrationTest extends IntegrationTest {

    private Member member1;
    private Member member2;

    @BeforeEach
    void setUp() {
        member1 = Member.createNewMember("닉네임1", "test1@gmail.com", "소개글1",
                "프로필url1", 36.5);
        member2 = Member.createNewMember("닉네임2", "test2@gmail.com", "소개글2",
                "프로필url2", 40.0);

        entityManager.persist(member1);
        entityManager.persist(member2);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void 멤버_조회_테스트() throws Exception {
        // given, when
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/members/{memberId}", member1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // then
        String json = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        MemberResponse response = objectMapper.readValue(json, MemberResponse.class);

        SoftAssertions.assertSoftly((softly) -> {
            softly.assertThat(response.id()).isEqualTo(member1.getId());
            softly.assertThat(response.nickname()).isEqualTo(member1.getNickname());
            softly.assertThat(response.introduction()).isEqualTo(member1.getIntroduction());
            softly.assertThat(response.profile()).isEqualTo(member1.getProfile());
            softly.assertThat(response.credibility()).isEqualTo(member1.getCredibility());
        });
    }
}
