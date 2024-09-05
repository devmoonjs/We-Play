package newsfeed.weplay.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import newsfeed.weplay.domain.auth.dto.AuthUser;
import newsfeed.weplay.domain.comment.dto.request.CommentRequestDto;
import newsfeed.weplay.domain.comment.dto.request.CommentSaveRequestDto;
import newsfeed.weplay.domain.comment.dto.response.CommentSearchResponseDto;
import newsfeed.weplay.domain.comment.service.CommentService;
import newsfeed.weplay.domain.filter.annotaion.Auth;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<String> postComment(@PathVariable Long postId
            , @RequestBody CommentSaveRequestDto requestDto, @Auth AuthUser authUser) {
        commentService.postComment(postId, requestDto, authUser);
        return ResponseEntity.ok().body("댓글이 작성 되었습니다.");
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentSearchResponseDto>> searchPostComment(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.searchPostComment(postId));
    }

    @GetMapping("/posts/comments/{userId}")
    public ResponseEntity<List<CommentSearchResponseDto>> searchUserComment(@PathVariable Long userId) {
        return ResponseEntity.ok(commentService.searchUserComment(userId));
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable Long commentId
            , @RequestBody CommentRequestDto requestDto, @Auth AuthUser authUser) {
        commentService.updateComment(commentId, requestDto, authUser);
        return ResponseEntity.ok().body("댓글이 수정 되었습니다.");
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId, @Auth AuthUser authUser) {
        commentService.deleteComment(commentId, authUser);
        return ResponseEntity.ok().body("댓글이 삭제 되었습니다.");
    }

    @GetMapping("/comments/{commentId}/reports")
    public ResponseEntity<String> reportComment(@PathVariable Long commentId, @Auth AuthUser authUser, @RequestBody CommentRequestDto requestDto){
        return ResponseEntity.ok().body(commentService.reportComment(commentId, authUser, requestDto));
    }
}
