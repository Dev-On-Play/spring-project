package mos.mogako.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import jakarta.persistence.EntityManager;
import mos.category.entity.Category;
import mos.hashtag.entity.Hashtag;
import mos.member.entity.Member;
import mos.mogako.entity.Mogako;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
public class MogakoRepositoryTest {

    @Autowired
    private MogakoRepository mogakoRepository;

    @Autowired
    private EntityManager entityManager;

    private Member member1;
    private Category category1;
    private Category category2;
    private Hashtag hashtag1;
    private Hashtag hashtag2;
    private Mogako category1NoHashtag;
    private Mogako category1Hashtag1;
    private Mogako category1Hashtag2;
    private Mogako category1Hashtag12;
    private Mogako category2Hashtag1;
    private Mogako category2Hashtag2;
    private Mogako category2Hashtag12;
    private int totalMogakoCounts;

    @BeforeEach
    void setUp() {
        member1 = Member.createNewMember("member1", "email", "profile");

        category1 = Category.createCategory("category1");
        category2 = Category.createCategory("category2");

        hashtag1 = Hashtag.createNewHashtag("hashtag1");
        hashtag2 = Hashtag.createNewHashtag("hashtag2");

        List<Hashtag> hashtags1 = List.of(hashtag1);
        List<Hashtag> hashtags2 = List.of(hashtag2);
        List<Hashtag> hashtags12 = List.of(hashtag1, hashtag2);

        category1NoHashtag = Mogako.createNewMogako("카테고리1 모각코", "모각코 짧은 소개",
                category1, List.of(),
                LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(2L),
                8, 2,
                "모각코 상세설명");

        category1Hashtag1 = Mogako.createNewMogako("카테고리1 해시태그1 모각코", "모각코 짧은 소개",
                category1, hashtags1,
                LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(2L),
                8, 2,
                "모각코 상세설명");

        category1Hashtag2 = Mogako.createNewMogako("카테고리1 해시태그2 모각코", "모각코 짧은 소개",
                category1, hashtags2,
                LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(2L),
                8, 2,
                "모각코 상세설명");

        category1Hashtag12 = Mogako.createNewMogako("카테고리1 해시태그12 모각코", "모각코 짧은 소개",
                category1, hashtags12,
                LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(2L),
                8, 2,
                "모각코 상세설명");

        category2Hashtag1 = Mogako.createNewMogako("카테고리2 해시태그1 모각코", "모각코 짧은 소개",
                category2, hashtags1,
                LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(2L),
                8, 2,
                "모각코 상세설명");

        category2Hashtag2 = Mogako.createNewMogako("카테고리2 해시태그2 모각코", "모각코 짧은 소개",
                category2, hashtags2,
                LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(2L),
                8, 2,
                "모각코 상세설명");

        category2Hashtag12 = Mogako.createNewMogako("카테고리2 해시태그12 모각코", "모각코 짧은 소개",
                category2, hashtags12,
                LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(2L),
                8, 2,
                "모각코 상세설명");

        totalMogakoCounts = 7;

        entityManager.persist(member1);
        entityManager.persist(category1);
        entityManager.persist(category2);
        entityManager.persist(hashtag1);
        entityManager.persist(hashtag2);

        entityManager.persist(category1NoHashtag);
        entityManager.persist(category1Hashtag1);
        entityManager.persist(category1Hashtag2);
        entityManager.persist(category1Hashtag12);
        entityManager.persist(category2Hashtag1);
        entityManager.persist(category2Hashtag2);
        entityManager.persist(category2Hashtag12);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void 카테고리_해시태그_동적쿼리() {
        //given
        List<Long> categoryIds = null;
        List<Long> hashtagIds = List.of(hashtag1.getId());
        PageRequest pageRequest = PageRequest.of(0, 100);

        //when
        Page<Mogako> results = mogakoRepository.findAllWithFiltering(categoryIds, hashtagIds, pageRequest);

        //then
        for (Mogako mogako : results.getContent()) {
            System.out.println("mogako = " + mogako);
        }

    }

    @Test
    void 아무_조건도_입력되지_않으면_전체_모각코를_페이징해서_반환한다() {
        //given
        List<Long> categoryIds = null;
        List<Long> hashtagIds = null;

        int pageSize = 2;
        PageRequest pageRequest = PageRequest.of(0, pageSize);

        //when
        Page<Mogako> results = mogakoRepository.findAllWithFiltering(categoryIds, hashtagIds, pageRequest);

        //then
        assertSoftly(softly -> {
            softly.assertThat(results.getContent().size()).isEqualTo(pageSize);
            softly.assertThat(results.getTotalElements()).isEqualTo(totalMogakoCounts);
            softly.assertThat(results.getTotalPages()).isEqualTo((totalMogakoCounts / pageSize) + 1);
        });
    }

    @Test
    void 카테고리_조건만_입력되면_카테고리만_필터링해서_반환한다() {
        //given
        List<Long> categoryIds = List.of(category1.getId());
        List<Long> hashtagIds = null;
        int pageSize = 10;
        PageRequest pageRequest = PageRequest.of(0, pageSize);

        //when
        Page<Mogako> results = mogakoRepository.findAllWithFiltering(categoryIds, hashtagIds, pageRequest);

        //then
        assertThat(results.getContent()).extracting("id")
                .containsExactly(
                        category1NoHashtag.getId(),
                        category1Hashtag1.getId(),
                        category1Hashtag2.getId(),
                        category1Hashtag12.getId());
    }

    @Test
    void 카테고리_필터링_조건은_or_조건으로_적용된다() {
        //given
        List<Long> categoryIds = List.of(category1.getId(), category2.getId());
        List<Long> hashtagIds = null;
        int pageSize = 10;
        PageRequest pageRequest = PageRequest.of(0, pageSize);

        //when
        Page<Mogako> results = mogakoRepository.findAllWithFiltering(categoryIds, hashtagIds, pageRequest);

        //then
        assertThat(results.getContent()).extracting("id")
                .containsExactly(
                        category1NoHashtag.getId(),
                        category1Hashtag1.getId(),
                        category1Hashtag2.getId(),
                        category1Hashtag12.getId(),
                        category2Hashtag1.getId(),
                        category2Hashtag2.getId(),
                        category2Hashtag12.getId());
    }

    @Test
    void 해시태그_조건이_입력되면_카테고리와_해시태그_조건으로_필터링해서_반환한다() {
        //given
        List<Long> categoryIds = List.of(category1.getId());
        List<Long> hashtagIds = List.of(hashtag1.getId());
        int pageSize = 10;
        PageRequest pageRequest = PageRequest.of(0, pageSize);

        //when
        Page<Mogako> results = mogakoRepository.findAllWithFiltering(categoryIds, hashtagIds, pageRequest);

        //then
        assertThat(results.getContent()).extracting("id")
                .containsExactly(
                        category1Hashtag1.getId(),
                        category1Hashtag12.getId());
    }

    @Test
    void 해시태그_조건은_and_조건으로_적용된다() {
        //given
        List<Long> categoryIds = List.of(category1.getId(), category2.getId());
        List<Long> hashtagIds = List.of(hashtag1.getId(), hashtag2.getId());
        int pageSize = 10;
        PageRequest pageRequest = PageRequest.of(0, pageSize);

        //when
        Page<Mogako> results = mogakoRepository.findAllWithFiltering(categoryIds, hashtagIds, pageRequest);

        //then
        assertThat(results.getContent()).extracting("id")
                .containsExactly(
                        category1Hashtag12.getId(),
                        category2Hashtag12.getId());
    }
}
