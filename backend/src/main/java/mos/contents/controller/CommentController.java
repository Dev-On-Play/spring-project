package mos.contents.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mos.contents.dto.CommentsResponse;
import mos.contents.dto.CreateCommentRequest;
import mos.contents.service.CommentService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "댓글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/mogakos/{mogako_id}/comments")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "모각코 상세 페이지>댓글 전체 조회")
    @GetMapping("/")
    public ResponseEntity<CommentsResponse> findComments(@PathVariable Long mogako_id, Pageable pageable) throws Exception {
        CommentsResponse commentsResponse = commentService.findAllByMogakoId(mogako_id, pageable);
        return ResponseEntity.ok(commentsResponse);
    }

    @Operation(summary = "모각코 상세 페이지>댓글 생성")
    @PostMapping("/create")
    public ResponseEntity<Long> createComment(@PathVariable Long mogako_id, @RequestBody CreateCommentRequest request) {
        Long createCommentId = commentService.createComent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createCommentId);
    }

    @Operation(summary = "모각코 상세 페이지>대댓글 생성")
    @PostMapping("/create/{parents_id}")
    public ResponseEntity<Long> createChildComment(@PathVariable Long mogako_id, @PathVariable String parents_id, @RequestBody CreateCommentRequest request) {
        Long createCommentId = commentService.createComent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createCommentId);
    }


}
