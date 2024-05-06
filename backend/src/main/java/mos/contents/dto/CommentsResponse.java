package mos.contents.dto;

import mos.contents.entity.Comment;
import org.springframework.data.domain.Page;

import java.util.List;

public record CommentsResponse (List<CommentResponse> comments,int totalPage,int pageNumber){

    public static CommentsResponse from (Page<Comment> page){
        Page<CommentResponse> responses = page.map(CommentResponse::from);
        return new CommentsResponse(responses.getContent(), responses.getTotalPages(), responses.getNumber());
    }

}
