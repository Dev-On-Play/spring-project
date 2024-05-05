package mos.contents.dto;

import java.util.List;

public record CommentsResponse (Integer totalPage, List<CommentResponse> comments){
}
