package newsfeed.weplay.domain.like.controller;

import lombok.RequiredArgsConstructor;
import newsfeed.weplay.domain.auth.dto.AuthUser;
import newsfeed.weplay.domain.filter.annotaion.Auth;
import newsfeed.weplay.domain.like.dto.CommentLikeRequestDto;
import newsfeed.weplay.domain.like.dto.PostLikeRequestDto;
import newsfeed.weplay.domain.like.service.LikeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/posts/{postId}/like") //게시글 좋아요
    public void upLikePost (@PathVariable Long postId , @Auth AuthUser authUser){
        likeService.likePost(postId,authUser);
    }

    @DeleteMapping("/posts/{postId}/like") //게시글 좋아요 삭제
    public void deleteLikePost(@PathVariable Long postId,@Auth AuthUser authUser){
        likeService.deleteLikePost(postId,authUser);
    }

    @PostMapping("/comments/{commentId}/like")
    public void upLikeComment(@PathVariable Long commentId, @Auth AuthUser authUser){
        likeService.likeComment(commentId,authUser);
    }

    @DeleteMapping("/comments/{commentId}/like")
    public void deleteLikeComment(@PathVariable Long commentId,@Auth AuthUser authUser){
        likeService.deleteLikeComment(commentId,authUser);
    }
}
