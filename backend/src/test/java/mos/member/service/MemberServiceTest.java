package mos.member.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import mos.member.dto.MemberResponse;
import mos.member.entity.Member;
import org.assertj.core.api.SoftAssertions;
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
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private EntityManager entityManager;

    private Member member1;

    @BeforeEach
    void setUp() {
        member1 = Member.createNewMember("닉네임1", "test1@gmail.com", "프로필url1");

        entityManager.persist(member1);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void 멤버_id로_해당_멤버를_조회한다() {
        // given, when
        MemberResponse response = memberService.findMember(member1.getId());

        // then
        SoftAssertions.assertSoftly((softly) -> {
            softly.assertThat(response.id()).isEqualTo(member1.getId());
            softly.assertThat(response.nickname()).isEqualTo(member1.getNickname());
        });
    }
}
