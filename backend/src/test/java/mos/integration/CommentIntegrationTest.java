package mos.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import mos.category.entity.Category;
import mos.contents.dto.CommentResponse;
import mos.contents.dto.CommentsResponse;
import mos.contents.dto.CreateCommentRequest;
import mos.contents.entity.Comment;
import mos.member.entity.Member;
import mos.mogako.entity.Mogako;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;


class CommentIntegrationTest extends IntegrationTest {

    private Comment comment1;
    private Comment comment2;
    private Comment childComment1;
    private Comment childComment2;

    private Category category;
    private Mogako mogako;
    private Member member;

    @BeforeEach
    void setup() {
        category = Category.createCategory("category");
        mogako = Mogako.createNewMogako("mogakp", "summary", category,
                LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(2L),
                8, 2,
                "모각코 상세설명");
        member = Member.createNewMember("nick", "aaa@aaa.aa", "profileurl");
        comment1 = Comment.createNewComment(mogako, member, "신규 댓글 1");
        comment2 = Comment.createNewComment(mogako, member, "신규 댓글 2");

        entityManager.persist(category);
        entityManager.persist(mogako);
        entityManager.persist(member);
        entityManager.persist(comment1);
        entityManager.persist(comment2);

        childComment1 = Comment.createNewChildComment(mogako, member, comment1, "대댓글 1");
        childComment2 = Comment.createNewChildComment(mogako, member, comment1, "대댓글 2");

        entityManager.persist(childComment1);
        entityManager.persist(childComment2);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void 댓글_생성_테스트() throws Exception {
        //given
        CreateCommentRequest request = new CreateCommentRequest(mogako.getId(), member.getId(), 0L, "댓글 테스트");
        String jsonRequest = objectMapper.writeValueAsString(request);

        Long beforeCommentCount = entityManager.createQuery("select count(*) from Comment c", Long.class)
                .getSingleResult();
        //when
        this.mockMvc.perform(post("/api/mogakos/" + mogako.getId() + "/comments/create")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        //then
        Long afterCommentCount = entityManager.createQuery("select count(*) from Comment c", Long.class)
                .getSingleResult();
        assertThat(afterCommentCount).isEqualTo(beforeCommentCount + 1);
    }

    @Test
    void 대댓글_생성_테스트() throws Exception {
        //given
        CreateCommentRequest request = new CreateCommentRequest(mogako.getId(), member.getId(), comment1.getId(), "댓글 테스트");
        String jsonRequest = objectMapper.writeValueAsString(request);

        Long beforeChildCommentCount = entityManager.createQuery("select count(*) from Comment c where parent.id != 0L", Long.class)
                .getSingleResult();
        //when
        this.mockMvc.perform(post("/api/mogakos/" + mogako.getId() + "/comments/create/" + comment1.getId())
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        //then
        Long afterChildCommentCount = entityManager.createQuery("select count(*) from Comment c where parent.id != 0L", Long.class)
                .getSingleResult();
        assertThat(afterChildCommentCount).isEqualTo(beforeChildCommentCount + 1);
    }

    @Test
    void 모각코의_전체_댓글_조회_테스트() throws Exception {
        //given
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        int pageNum = 0;
        int pageSize = 2;
        params.add("page", String.valueOf(pageNum));
        params.add("size", String.valueOf(pageSize));
        //when
        MvcResult result = this.mockMvc.perform(get("/api/mogakos/" + mogako.getId() + "/comments/")
                        .queryParams(params)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        //then
        String json = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        CommentsResponse response = objectMapper.readValue(json, CommentsResponse.class);
        List<CommentResponse> Coments = response.comments();

        SoftAssertions.assertSoftly((softly) -> {
            softly.assertThat(response.pageNumber()).isEqualTo(pageNum);
            softly.assertThat(Coments.size()).isEqualTo(pageSize);
        });
    }

}
