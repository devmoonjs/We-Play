package newsfeed.weplay.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import newsfeed.weplay.domain.auth.dto.AuthUser;
import newsfeed.weplay.domain.comment.dto.request.CommentRequestDto;
import newsfeed.weplay.domain.comment.dto.request.CommentSaveRequestDto;
import newsfeed.weplay.domain.comment.dto.response.CommentResponseDto;
import newsfeed.weplay.domain.comment.dto.response.CommentSaveResponseDto;
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
    public ResponseEntity<CommentSaveResponseDto> postComment(@PathVariable Long postId
            , @RequestBody CommentSaveRequestDto requestDto, @Auth AuthUser authUser) {
        return ResponseEntity.ok(commentService.postComment(postId, requestDto, authUser));
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
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId
            , @RequestBody CommentRequestDto requestDto, @Auth AuthUser authUser) {
        return ResponseEntity.ok(commentService.updateComment(commentId, requestDto, authUser));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId, @Auth AuthUser authUser) {
        return commentService.deleteComment(commentId, authUser);
    }

    @GetMapping("/comments/{commentId}/reports")
    public ResponseEntity<String> reportComment(@PathVariable Long commentId, @Auth AuthUser authUser, @RequestBody CommentRequestDto requestDto){
        return commentService.reportComment(commentId, authUser, requestDto);
    }
}
