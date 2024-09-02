package newsfeed.weplay.domain.like.controller;

import lombok.RequiredArgsConstructor;
import newsfeed.weplay.domain.like.dto.CommentLikeRequestDto;
import newsfeed.weplay.domain.like.dto.PostLikeRequestDto;
import newsfeed.weplay.domain.like.service.LikeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/post/{postId}/like") //게시글 좋아요
    public void upLikePost (@PathVariable Long postId , @RequestBody PostLikeRequestDto postLikeRequestDto){
        likeService.upLikePost(postId,postLikeRequestDto);
    }

    @DeleteMapping("/post/{postId}/like") //게시글 좋아요 삭제
    public void deleteLikePost(@PathVariable Long postId,@RequestBody PostLikeRequestDto postLikeRequestDto){
        likeService.deleteLikePost(postId,postLikeRequestDto);
    }

    @PostMapping("/comment/{commentId}/like")
    public void upLikeComment(@PathVariable Long commentId, @RequestBody CommentLikeRequestDto commentLikeRequestDto){
        likeService.upLikeComment(commentId,commentLikeRequestDto);
    }

    @DeleteMapping("/comment/{commentId}/like")
    public void deleteLikeComment(@PathVariable Long commentId,@RequestBody CommentLikeRequestDto commentLikeRequestDto){
        likeService.deleteLikeComment(commentId,commentLikeRequestDto);
    }
}
