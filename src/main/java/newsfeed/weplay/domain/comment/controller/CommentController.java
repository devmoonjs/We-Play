package newsfeed.weplay.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import newsfeed.weplay.domain.comment.dto.request.CommentRequestDto;
import newsfeed.weplay.domain.comment.dto.response.CommentSaveResponseDto;
import newsfeed.weplay.domain.comment.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{post_id}/comments")
    public ResponseEntity<CommentSaveResponseDto> postComment(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto) {
        return ResponseEntity.ok(commentService.postComment(postId, requestDto));
    }

    @GetMapping("/posts/{post_id}/comments")
    public ResponseEntity<List<CommentSaveResponseDto>> searchPostComment(@PathVariable Long postId){
        return ResponseEntity.ok(commentService.searchPostComment(postId));
    }


}
