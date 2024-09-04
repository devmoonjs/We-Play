package newsfeed.weplay.domain.like.service;

import lombok.RequiredArgsConstructor;
import newsfeed.weplay.domain.auth.dto.AuthUser;
import newsfeed.weplay.domain.comment.entity.Comment;
import newsfeed.weplay.domain.comment.repository.CommentRepository;
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

    @Transactional
    public void likePost(Long postId,AuthUser authUser) {
        User user = userRepository.findById(authUser.getUserId()).orElseThrow(() -> new NullPointerException("해당 유저가 존재 하지 않습니다."));
        Post post = postRepository.findById(postId).orElseThrow(() -> new NullPointerException("해당 게시글이 존재하지 않습니다."));

        //게시글의 유저가 있는지 확인 그리고 해당게시글의 유저id와 좋아요 하려는 유저가 같은 사람인지 확인하는 조건문
        if(post.getUser() != null && ObjectUtils.nullSafeEquals(user.getId(),post.getUser().getId())) {
            throw new NullPointerException("자신이 작성한 게시글에는 좋아요를 누를 수 없습니다.");
        }//같은사람이 아니라면 like를 저장
        if(likesRepository.findByUserAndPost(user,post).isPresent()){
            throw new NullPointerException("이미 좋아요한 게시글 입니다.");
        }
        likesRepository.save(new Likes(user,post));
        post.increaseLikeCount();
    }

    @Transactional
    public void deleteLikePost(Long postId,AuthUser authUser) {
        //게시글이 있는지 확인
        Post post = postRepository.findById(postId).orElseThrow(() -> new NullPointerException("해당 게시글이 존재하지 않습니다."));
        //유저 id 확인
        User user = userRepository.findById(authUser.getUserId()).orElseThrow(() -> new NullPointerException("해당 유저가 존재 하지 않습니다."));
        //유저와 게시글 확인해서 해당 유저가 좋아요했는지 확인
        Likes like = likesRepository.findByUserAndPost(user,post).orElseThrow(() -> new NullPointerException("좋아요를 하지 않았습니다."));

        likesRepository.delete(like);
        post.decreaseLikeCount();
    }

    @Transactional
    public void likeComment(Long commentId, AuthUser authUser) {
        User user = userRepository.findById(authUser.getUserId()).orElseThrow(() -> new NullPointerException("해당 유저가 존재 하지 않습니다."));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NullPointerException("해당 댓글이 존재 하지 않습니다."));

        if(comment.getUser() != null && ObjectUtils.nullSafeEquals(user.getId(),comment.getUser().getId())) {
            throw new NullPointerException("자신이 작성한 댓글에는 좋아요를 누를 수 없습니다.");
        }//같은사람이 아니라면 like를 저장

        if(likesRepository.findByUserAndComment(user,comment).isPresent()){
            throw new NullPointerException("이미 좋아요한 댓글 입니다.");
        }

        likesRepository.save(new Likes(user,comment));
        comment.increaseLikeCount();
    }

    @Transactional
    public void deleteLikeComment(Long commentId,AuthUser authUser) {
        //댓글이 있는지 확인
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NullPointerException("해당 댓글이 존재하지 않습니다."));
        //유저 id 확인
        User user = userRepository.findById(authUser.getUserId()).orElseThrow(() -> new NullPointerException("해당 유저가 존재 하지 않습니다."));
        //유저와 게시글 확인해서 해당 유저가 좋아요했는지 확인
        Likes like = likesRepository.findByUserAndComment(user,comment).orElseThrow(() -> new NullPointerException("좋아요를 하지 않았습니다."));

        likesRepository.delete(like);
        comment.decreaseLikeCount();
    }
}
