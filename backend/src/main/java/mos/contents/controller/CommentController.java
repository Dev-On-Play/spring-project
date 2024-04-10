package mos.contents.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mos.contents.dto.CommentsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

import java.util.ArrayList;

@Tag(name = "댓글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/mogakos/{mogako_id}/comments")
public class CommentController {

    @Operation(summary = "모각코 상세 페이지>댓글 전체 조회")
    @GetMapping("/")
    public ResponseEntity<CommentsResponse> findComments(@PathVariable Long mogako_id, Pageable pageable){
        CommentsResponse commentsResponse = new CommentsResponse(1, new ArrayList<>());
        return ResponseEntity.ok(commentsResponse);
    }
    @Operation(summary = "모각코 상세 페이지>댓글 생성")
    @PostMapping("/create")
    public ResponseEntity<Long> createComment(@PathVariable Long mogako_id){
        Long createCommentId = 0L;
        return ResponseEntity.status(HttpStatus.OK).body(createCommentId);
    }

}
