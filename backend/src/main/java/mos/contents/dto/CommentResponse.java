package mos.contents.dto;

import mos.contents.entity.Comment;
import mos.member.entity.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public record CommentResponse(long id, long mogako_id, CommentMember member, ChildComments childComments, String contents, LocalDateTime created_date){

    //user entity 수정 필요
    static record CommentMember(long id, String nickname, String profile){
        public static CommentMember from(Member member){
            return new CommentMember(member.getId(),member.getName(),"profileurl");
        }
    }

    static record ChildComments(List<ChildComment> childList){

        public static ChildComments from(List<Comment> commentList) {
            List<ChildComment> comments = commentList.stream().map(ChildComment::from).toList();
            return new ChildComments(comments);
        }
    }
    static record ChildComment(long id, long mogako_id, CommentMember member, Long parents_id, String contents, LocalDateTime created_date){
        public static ChildComment from(Comment comment){
            return new ChildComment(
                    comment.getId(),
                    comment.getMogako().getId(),
                    CommentMember.from(comment.getMember()),
                    comment.getParent().getId(),
                    comment.getContents(),
                    comment.getCreatedDate()
            );
        }
    }


    public static CommentResponse from(Comment comment){
        return new CommentResponse(comment.getId(),
                comment.getMogako().getId(),
                CommentMember.from(comment.getMember()),
                ChildComments.from(comment.getComments()),
                comment.getContents(),
                comment.getCreatedDate()
        );
    }
}