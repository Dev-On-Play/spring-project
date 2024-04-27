package mos.mogako.service;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import mos.mogako.dto.CreateMogakoRequest;
import mos.mogako.dto.MogakoResponse;
import mos.mogako.entity.Mogako;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@Transactional
@SpringBootTest
class MogakoServiceTest {

    @Autowired
    private MogakoService mogakoService;

    @Autowired
    private EntityManager entityManager;

    @Test
    void 신규_모각코를_생성한다() {
        // given
        Long categoryId = 1L;
        CreateMogakoRequest request = new CreateMogakoRequest(categoryId,
                "모각코 이름", "모각코 짧은 소개",
                LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(2L),
                8, 2,
                "모각코 상세설명");

        // when
        Long result = mogakoService.createMogako(request);

        // then
        assertThat(result).isNotNull();
    }

    @Test
    void 모각코_id로_해당_모각코를_조회한다() {
        // given
        Mogako mogako1 = Mogako.createNewMogako("모각코 이름", "모각코 짧은 소개",
                LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(2L),
                8, 2,
                "모각코 상세설명");
        entityManager.persist(mogako1);

        entityManager.flush();
        entityManager.clear();

        // when
        MogakoResponse response = mogakoService.findMogako(mogako1.getId());

        // then
        SoftAssertions.assertSoftly((softly) -> {
            softly.assertThat(response.id()).isEqualTo(mogako1.getId());
            softly.assertThat(response.name()).isEqualTo(mogako1.getName());
        });

    }
}