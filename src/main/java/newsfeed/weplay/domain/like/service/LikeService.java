package newsfeed.weplay.domain.like.service;

import lombok.RequiredArgsConstructor;
import newsfeed.weplay.domain.comment.entity.Comment;
import newsfeed.weplay.domain.comment.repository.CommentRepository;
import newsfeed.weplay.domain.like.dto.CommentLikeRequestDto;
import newsfeed.weplay.domain.like.dto.PostLikeRequestDto;
import newsfeed.weplay.domain.like.entity.Likes;
import newsfeed.weplay.domain.like.repository.LikesRepository;
import newsfeed.weplay.domain.post.entity.Post;
import newsfeed.weplay.domain.post.repository.PostRepository;
import newsfeed.weplay.domain.user.entity.User;
import newsfeed.weplay.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    private final LikesRepository likesRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public void likePost(Long postId, PostLikeRequestDto postLikeRequestDto) {
        User user = userRepository.findById(postLikeRequestDto.getUserId()).orElseThrow(() -> new NullPointerException());
        Post post = postRepository.findById(postId).orElseThrow(() -> new NullPointerException("해당 게시글이 존재하지 않습니다."));
        //게시글의 유저가 있는지 확인 그리고 해당게시글의 유저id와 좋아요 하려는 유저가 같은 사람인지 확인하는 조건문
        if(post.getUserId() != null && ObjectUtils.nullSafeEquals(user.getId(),post.getUserId())) {
            throw new NullPointerException("자신이 작성한 게시글에는 좋아요를 누를 수 없습니다.");
        }//같은사람이 아니라면 like를 저장
        likesRepository.save(new Likes(user,post));
        post.increaseLikeCount();
    }

    public void deleteLikePost(Long postId,PostLikeRequestDto postLikeRequestDto) {
        //게시글이 있는지 확인
        Post post = postRepository.findById(postId).orElseThrow(() -> new NullPointerException("해당 게시글이 존재하지 않습니다."));
        //유저 id 확인
        User user = userRepository.findById(postLikeRequestDto.getUserId()).orElseThrow(() -> new NullPointerException());
        //유저와 게시글 확인해서 해당 유저가 좋아요했는지 확인
        Likes like = likesRepository.findByUserAndPost(user,post).orElseThrow(() -> new NullPointerException("좋아요를 하지 않았습니다."));

        likesRepository.delete(like);
        post.decreaseLikeCount();
    }

    public void likeComment(Long commentId, CommentLikeRequestDto commentLikeRequestDto) {
        User user = userRepository.findById(commentLikeRequestDto.getUserId()).orElseThrow(() -> new NullPointerException());
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NullPointerException());

        if(comment.getUser() != null && ObjectUtils.nullSafeEquals(user.getId(),comment.getUser().getId())) {
            throw new NullPointerException("자신이 작성한 댓글에는 좋아요를 누를 수 없습니다.");
        }//같은사람이 아니라면 like를 저장
        likesRepository.save(new Likes(user,comment));
        comment.increaseLikeCount();
    }

    public void deleteLikeComment(Long commentId,CommentLikeRequestDto commentLikeRequestDto) {
        //댓글이 있는지 확인
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NullPointerException("해당 댓글이 존재하지 않습니다."));
        //유저 id 확인
        User user = userRepository.findById(commentLikeRequestDto.getUserId()).orElseThrow(() -> new NullPointerException());
        //유저와 게시글 확인해서 해당 유저가 좋아요했는지 확인
        Likes like = likesRepository.findByUserAndComment(user,comment).orElseThrow(() -> new NullPointerException("좋아요를 하지 않았습니다."));

        likesRepository.delete(like);
        comment.decreaseLikeCount();
    }
}
