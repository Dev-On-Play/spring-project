package mos.contents.entity;

import mos.member.entity.Member;
import mos.mogako.entity.Mogako;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

public class CommentTest {
    Mogako mogako;
    Member member;
    Comment pComment;
    @BeforeEach
    void setUp() {
        mogako = mock(Mogako.class);
        member = mock(Member.class);
        pComment = mock(Comment.class);

    }
    @Test
    void 댓글_생성_테스트(){
        assertDoesNotThrow(() -> Comment.createNewComment(mogako, member, "댓글 생성 테스트"));
    }

    @Test
    void 대댓글_생성_테스트(){
        assertDoesNotThrow(() -> Comment.createNewChildComment(mogako, member, pComment,"댓글 생성 테스트"));
    }

}
