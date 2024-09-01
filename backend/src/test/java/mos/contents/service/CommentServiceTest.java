package mos.contents.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import mos.category.entity.Category;
import mos.contents.dto.CommentsResponse;
import mos.contents.dto.CreateCommentRequest;
import mos.contents.entity.Comment;
import mos.hashtag.entity.Hashtag;
import mos.member.entity.Member;
import mos.mogako.entity.Mogako;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

//@Disabled("테스트 코드 미완성으로 인한 임시 비활성화")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Transactional
@SpringBootTest
class CommentServiceTest {

    @Autowired
    private CommentService commentService;
    @Autowired
    private EntityManager entityManager;
    private Comment comment;

    Mogako mogako;
    Member member;
    Category category;
    private Comment pComment;

    @BeforeEach
    void setup() {
        category = Category.createCategory("category1");
        Hashtag hashtag1 = Hashtag.createNewHashtag("hashtag1");
        Hashtag hashtag2 = Hashtag.createNewHashtag("hashtag2");
        List<Hashtag> hashtags = List.of(hashtag1, hashtag2);
        mogako = Mogako.createNewMogako("samplemogakp", "summary", category, hashtags,
                LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(2L),
                8, 2,
                "detailcontent");
        member = Member.createNewMember("nick", "aaa@aaa.aaa", "profileurl");
        pComment = Comment.createNewComment(mogako, member, "댓글 테스트");
        entityManager.persist(category);
        entityManager.persist(mogako);
        entityManager.persist(member);
        entityManager.persist(pComment);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void 댓글_생성() {
        //given
        CreateCommentRequest request = new CreateCommentRequest(mogako.getId(), member.getId(), 0L, "신규 댓글 생성");
        //when
        Long result = commentService.createComent(request);
        //then
        assertThat(result).isNotNull();
    }

    @Test
    void 대댓글_생성() {
        //TODO:member entity 완성 이후 service 수정하여 다시 테스트 예정
        //given
        CreateCommentRequest request = new CreateCommentRequest(mogako.getId(), member.getId(), pComment.getId(), "신규 대댓글 생성");
        //when
        Long result = commentService.createComent(request);
        //then
        assertThat(result).isNotNull();
    }

    @Test
    void 모각코_id로_댓글목록_페이지조회() throws Exception {
        //given
        int pageNumber = 1;
        int pageSize = 2;
        int totalElements = 10;

        for (int i = 1; i < totalElements; i++) {
            //TODO:member entity 완성 이후 service 수정하여 다시 테스트 예정
            entityManager.persist(Comment.createNewComment(mogako, member, "댓글 테스트" + i));
        }
        entityManager.flush();
        entityManager.clear();

        PageRequest request = PageRequest.of(pageNumber, pageSize);

        //when
        CommentsResponse response = commentService.findAllByMogakoId(mogako.getId(), request);

        //then
        assertSoftly((softly) -> {
            softly.assertThat(response.pageNumber()).isEqualTo(pageNumber);
            softly.assertThat(response.comments().size()).isEqualTo(pageSize);
            softly.assertThat(response.totalPage()).isEqualTo(totalElements / pageSize);
        });
    }

}
