package mos.contents.dto;

import mos.contents.entity.Comment;
import mos.member.entity.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record CommentResponse(long id, long mogako_id, CommentMember member, ChildComments childComments, String contents, LocalDateTime created_date){

    //user entity 수정 필요
    static record CommentMember(long id, String nickname, String profile){
        public static CommentMember from(Member member){
            return new CommentMember(member.getId(),member.getName(),"profileurl");
        }
    }

    static record ChildComments(List<ChildComment> childList){

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
                new ChildComments(new ArrayList<>()),
                comment.getContents(),
                comment.getCreatedDate()
                );
    }
}