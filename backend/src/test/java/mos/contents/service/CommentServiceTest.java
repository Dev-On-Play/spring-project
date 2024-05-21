package mos.contents.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import mos.contents.dto.CommentsResponse;
import mos.contents.dto.CreateCommentRequest;
import mos.contents.entity.Comment;
import mos.member.entity.Member;
import mos.mogako.entity.Mogako;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.mock;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Transactional
@SpringBootTest
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;
    @Autowired
    private EntityManager entityManager;
    private Comment comment;

    Mogako mogako;
    Member member;
    private Comment pComment;

    @BeforeEach
    void setup(){
        mogako = mock(Mogako.class);
        member = mock(Member.class);
        pComment = Comment.createNewComment(mogako,member,"댓글 테스트");

        entityManager.persist(pComment);
        entityManager.flush();
        entityManager.clear();
    }
    @Test
    void 댓글_생성(){
        //given
        CreateCommentRequest request = new CreateCommentRequest(mogako.getId(), member.getId(), 0L,"신규 댓글 생성");
        //when
        Long result = commentService.createComent(request);
        //then
        assertThat(result).isNotNull();
    }

    @Test
    void 대댓글_생성(){
        //TODO:member entity 완성 이후 service 수정하여 다시 테스트 예정
        //given
        CreateCommentRequest request = new CreateCommentRequest(1L, 1L, pComment.getId(),"신규 대댓글 생성");
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

        for (int i = 1; i < totalElements; i++){
            //TODO:member entity 완성 이후 service 수정하여 다시 테스트 예정
            entityManager.persist(Comment.createNewComment(mogako,member,"댓글 테스트"+ i));
        }
        entityManager.flush();
        entityManager.clear();

        PageRequest request = PageRequest.of(pageNumber, pageSize);

        //when
        CommentsResponse response = commentService.findAllByMogakoId(mogako.getId(),request);

        //then
        assertSoftly((softly) -> {
            softly.assertThat(response.pageNumber()).isEqualTo(pageNumber);
            softly.assertThat(response.comments().size()).isEqualTo(pageSize);
            softly.assertThat(response.totalPage()).isEqualTo(totalElements/pageSize);
        });
    }

}
