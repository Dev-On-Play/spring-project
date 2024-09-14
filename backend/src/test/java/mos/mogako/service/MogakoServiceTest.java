package mos.mogako.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import mos.category.entity.Category;
import mos.hashtag.entity.Hashtag;
import mos.member.entity.Member;
import mos.mogako.dto.CreateMogakoRequest;
import mos.mogako.dto.MogakoResponse;
import mos.mogako.dto.MogakosResponse;
import mos.mogako.dto.UpdateMogakoRequest;
import mos.mogako.entity.Mogako;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@Transactional
@SpringBootTest
class MogakoServiceTest {

    @Autowired
    private MogakoService mogakoService;

    @Autowired
    private EntityManager entityManager;

    private Member member1;
    private Category category1;
    private Category category2;
    private Hashtag hashtag1;
    private Hashtag hashtag2;
    private Hashtag hashtag3;
    private Mogako mogako1;

    @BeforeEach
    void setUp() {
        member1 = Member.createNewMember("member1", "email", "profile");

        category1 = Category.createCategory("카테고리 이름1");
        category2 = Category.createCategory("카테고리 이름2");

        hashtag1 = Hashtag.createNewHashtag("hashtag1");
        hashtag2 = Hashtag.createNewHashtag("hashtag2");
        hashtag3 = Hashtag.createNewHashtag("hashtag3");
        List<Hashtag> hashtags = List.of(hashtag1, hashtag2);

        mogako1 = Mogako.createNewMogako("모각코 이름", "모각코 짧은 소개",
                category1, List.of(hashtag1, hashtag2),
                LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(2L),
                8, 2,
                "모각코 상세설명");

        entityManager.persist(member1);
        entityManager.persist(category1);
        entityManager.persist(category2);
        entityManager.persist(hashtag1);
        entityManager.persist(hashtag2);
        entityManager.persist(hashtag3);
        entityManager.persist(mogako1);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void 신규_모각코를_생성한다() {
        // given
        CreateMogakoRequest request = new CreateMogakoRequest("모각코 이름", "모각코 짧은 소개",
                category1.getId(), List.of(hashtag1.getId(), hashtag2.getId()),
                LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(2L),
                8, 2,
                "모각코 상세설명");

        // when
        Long result = mogakoService.createMogako(member1.getId(), request);

        // then
        assertThat(result).isNotNull();
    }

    @Test
    void 모각코_id로_해당_모각코를_조회한다() {
        // given, when
        MogakoResponse response = mogakoService.findMogako(mogako1.getId());

        // then
        assertSoftly((softly) -> {
            softly.assertThat(response.id()).isEqualTo(mogako1.getId());
            softly.assertThat(response.name()).isEqualTo(mogako1.getName());
        });
    }

    @Test
    void 단일_모각코의_정보를_수정할_수_있다() {
        // given
        Long updatedCategoryId = category2.getId();
        List<Long> updatedHashtagIds = List.of(hashtag2.getId(), hashtag3.getId());
        String updatedName = "모각코 이름 수정";
        String updatedSummary = "모각코 짧은 소개 수정";
        LocalDateTime updatedStartDate = LocalDateTime.now().plusDays(2L);
        LocalDateTime updatedEndDate = LocalDateTime.now().plusDays(3L);
        int updatedParticipantLimit = 10;
        int updatedMinimumParticipantCount = 4;
        String updatedDetailContent = "모각코 상세설명 수정";

        UpdateMogakoRequest request = new UpdateMogakoRequest(updatedName, updatedSummary,
                updatedCategoryId, updatedHashtagIds,
                updatedStartDate, updatedEndDate,
                updatedParticipantLimit, updatedMinimumParticipantCount,
                updatedDetailContent);

        // when
        Long updatedMogakoId = mogakoService.updateMogako(mogako1.getId(), request);

        // then
        entityManager.flush();
        entityManager.clear();
        Mogako updatedMogako = entityManager.find(Mogako.class, updatedMogakoId);
        List<Long> extractedHashtagIds = updatedMogako.getHashtags().stream()
                .map(Hashtag::getId)
                .toList();

        assertSoftly((softly) -> {
            softly.assertThat(updatedMogako.getCategory().getId()).isEqualTo(category2.getId());
            softly.assertThat(extractedHashtagIds).doesNotContain(hashtag1.getId());
            softly.assertThat(extractedHashtagIds).contains(hashtag2.getId(), hashtag3.getId());
            softly.assertThat(updatedMogako.getName()).isEqualTo(updatedName);
            softly.assertThat(updatedMogako.getSummary()).isEqualTo(updatedSummary);
            softly.assertThat(updatedMogako.getStartDate()).isEqualTo(updatedStartDate);
            softly.assertThat(updatedMogako.getEndDate()).isEqualTo(updatedEndDate);
            softly.assertThat(updatedMogako.getParticipantLimit()).isEqualTo(updatedParticipantLimit);
            softly.assertThat(updatedMogako.getMinimumParticipantCount()).isEqualTo(updatedMinimumParticipantCount);
            softly.assertThat(updatedMogako.getDetailContent()).isEqualTo(updatedDetailContent);
        });
    }

    @Test
    void 전체_모각코_목록을_페이지_조회한다() {
        // given
        int pageNumber = 1;
        int pageSize = 2;
        int totalElements = 10;

        for (int i = 1; i < totalElements; i++) {
            entityManager.persist(Mogako.createNewMogako("모각코 이름" + i, "모각코 짧은 소개",
                    category1, List.of(hashtag1, hashtag2),
                    LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(2L),
                    8, 2,
                    "모각코 상세설명"));
        }
        entityManager.flush();
        entityManager.clear();

        PageRequest request = PageRequest.of(pageNumber, pageSize);

        // when
        MogakosResponse response = mogakoService.findAllWithFiltering(null, null, request);

        // then
        assertSoftly((softly) -> {
            softly.assertThat(response.pageNumber()).isEqualTo(pageNumber);
            softly.assertThat(response.mogakos().size()).isEqualTo(pageSize);
            softly.assertThat(response.totalPages()).isEqualTo(totalElements / pageSize);
            softly.assertThat(response.totalElements()).isEqualTo(totalElements);
        });
    }
}
