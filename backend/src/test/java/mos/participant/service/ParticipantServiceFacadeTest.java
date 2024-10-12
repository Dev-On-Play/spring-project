package mos.participant.service;

import static org.assertj.core.api.Assertions.assertThat;

import mos.category.entity.Category;
import mos.category.repository.CategoryRepository;
import mos.hashtag.entity.Hashtag;
import mos.hashtag.repository.HashtagRepository;
import mos.member.entity.Member;
import mos.member.repository.MemberRepository;
import mos.mogako.entity.Mogako;
import mos.mogako.exception.ParticipantLimitExceededException;
import mos.mogako.repository.MogakoRepository;
import mos.util.CleanUp;
import mos.util.TestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Import(TestConfig.class)
@SpringBootTest
class ParticipantServiceFacadeTest {

    @Autowired
    private ParticipantServiceFacade participantServiceFacade;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private HashtagRepository hashtagRepository;

    @Autowired
    private MogakoRepository mogakoRepository;

    @Autowired
    private CleanUp cleanUp;

    private Member member0;
    private final List<Member> hundredMembers = new ArrayList<>();
    private Category category1;
    private Category category2;
    private Hashtag hashtag1;
    private Hashtag hashtag2;
    private Hashtag hashtag3;
    private Mogako mogako1;

    @BeforeEach
    void setUp() {
        member0 = Member.createNewMember("member0", "email", "profile");

        for (int num = 1; num <= 10; num++) {
            Member temp = Member.createNewMember("member" + num, "email", "profile");
            memberRepository.save(temp);
            hundredMembers.add(temp);
        }

        category1 = Category.createCategory("카테고리 이름1");
        category2 = Category.createCategory("카테고리 이름2");
        List<Category> categories = List.of(category1, category2);

        hashtag1 = Hashtag.createNewHashtag("hashtag1");
        hashtag2 = Hashtag.createNewHashtag("hashtag2");
        hashtag3 = Hashtag.createNewHashtag("hashtag3");
        List<Hashtag> hashtags = List.of(hashtag1, hashtag2);

        mogako1 = Mogako.createNewMogako("모각코 이름", "모각코 짧은 소개",
                category1, List.of(hashtag1, hashtag2),
                LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(2L),
                10, 2,
                "모각코 상세설명");

        memberRepository.save(member0);
        categoryRepository.saveAll(categories);
        hashtagRepository.saveAll(hashtags);
        mogakoRepository.save(mogako1);
    }

    @AfterEach
    void tearDown() {
        cleanUp.all();
    }

    @RepeatedTest(value = 20)
    void 인원_제한이_10명인_단일_모각코에_동시에_100명이_참여_신청해도_10명만_참여되어야_한다() throws InterruptedException {
        // given
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(110);
        CountDownLatch latch = new CountDownLatch(threadCount);

        AtomicInteger wasLimitExceptionCounter = new AtomicInteger();
        AtomicInteger jpaOptimisticLockExceptionCounter = new AtomicInteger();

        // when
        for (int i = 0; i < threadCount; i++) {
            final int memberNum = i;
            executorService.submit(() -> {
                try {
                    Member member = hundredMembers.get(memberNum);
                    participantServiceFacade.participate(member.getId(), mogako1.getId());
                } catch (ParticipantLimitExceededException e) {
                    wasLimitExceptionCounter.getAndIncrement();
                } catch (ObjectOptimisticLockingFailureException le) {
                    jpaOptimisticLockExceptionCounter.getAndIncrement();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        Mogako mogako = mogakoRepository.findByIdWithParticipants(mogako1.getId()).orElseThrow();

        // then
        assertThat(wasLimitExceptionCounter.get()).isZero();
        assertThat(jpaOptimisticLockExceptionCounter.get()).isZero();
        assertThat(mogako.getParticipants()).hasSize(mogako1.getParticipantLimit());
        assertThat(mogako.getCurrentParticipantCount()).isEqualTo(mogako.getParticipantLimit());
    }

}
