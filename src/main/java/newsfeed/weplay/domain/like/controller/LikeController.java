package newsfeed.weplay.domain.like.controller;

import lombok.RequiredArgsConstructor;
import newsfeed.weplay.domain.auth.dto.AuthUser;
import newsfeed.weplay.domain.filter.annotaion.Auth;
import newsfeed.weplay.domain.like.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {

    private final LikeService likeService;

    @GetMapping("/posts/{postId}/likes") //게시글 좋아요 생성
    public ResponseEntity<String> upLikePost (@PathVariable Long postId, @Auth AuthUser authUser){
        likeService.likePost(postId,authUser);
        return ResponseEntity.ok().body("게시글 좋아요 요청이 완료되었습니다.");
    }

    @DeleteMapping("/posts/{postId}/likes") //게시글 좋아요 삭제
    public ResponseEntity<String> deleteLikePost(@PathVariable Long postId,@Auth AuthUser authUser){
        likeService.deleteLikePost(postId,authUser);
        return ResponseEntity.ok().body("게시글 좋아요를 취소하였습니다.");
    }

    @GetMapping("/comments/{commentId}/likes") //댓글 좋아요 생성
    public ResponseEntity<String> upLikeComment(@PathVariable Long commentId, @Auth AuthUser authUser){
        likeService.likeComment(commentId,authUser);
        return ResponseEntity.ok().body("댓글 좋아요 요청이 완료되었습니다.");
    }

    @DeleteMapping("/comments/{commentId}/likes") //댓글 좋아요 삭제
    public ResponseEntity<String> deleteLikeComment(@PathVariable Long commentId,@Auth AuthUser authUser){
        likeService.deleteLikeComment(commentId,authUser);
        return ResponseEntity.ok().body("댓글 좋아요를 취소하였습니다.");
    }
}
