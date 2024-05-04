package mos.contents.dto;

public record CreateCommentRequest(Long mogako_id, Long member_id, Long parent_id, String contents) {
}
