package newsfeed.weplay.domain.comment.service;

import lombok.RequiredArgsConstructor;
import newsfeed.weplay.domain.auth.dto.AuthUser;
import newsfeed.weplay.domain.comment.dto.request.CommentRequestDto;
import newsfeed.weplay.domain.comment.dto.response.CommentResponseDto;
import newsfeed.weplay.domain.comment.dto.response.CommentSaveResponseDto;
import newsfeed.weplay.domain.comment.dto.response.CommentSearchResponseDto;
import newsfeed.weplay.domain.comment.entity.Comment;
import newsfeed.weplay.domain.comment.repository.CommentRepository;
import newsfeed.weplay.domain.post.entity.Post;
import newsfeed.weplay.domain.post.repository.PostRepository;
import newsfeed.weplay.domain.user.entity.User;
import newsfeed.weplay.domain.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentSaveResponseDto postComment(Long postId, CommentRequestDto requestDto, AuthUser authUser) {
        User user = userRepository.findById(authUser.getUserId()).orElseThrow();

        Post post = searchPost(postId);

        Comment comment = new Comment(requestDto, user, post);

        Comment saveComment = commentRepository.save(comment);

        return new CommentSaveResponseDto(saveComment);
    }

    public List<CommentSearchResponseDto> searchPostComment(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NullPointerException("해당 게시글이 없습니다."));
        List<Comment> commentList = post.getCommentList();

        return commentDtoList(commentList);
    }

    public List<CommentSearchResponseDto> searchUserComment(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NullPointerException("해당 유저가 없습니다."));

        List<Comment> commentList = user.getCommentList();

        return commentDtoList(commentList);
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, AuthUser authUser) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NullPointerException("해당 댓글이 없습니다."));

        User user = userRepository.findById(authUser.getUserId()).orElseThrow();

        if(!Objects.equals(comment.getUser().getId(), user.getId())){
            throw new IllegalArgumentException("댓글 수정 권한이 업습니다.");
        }

        comment.update(requestDto.getContent());

        return new CommentResponseDto(comment);
    }

    @Transactional
    public ResponseEntity<String> deleteComment(Long commentId, AuthUser authUser) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NullPointerException("해당 댓글이 없습니다."));

        User user = comment.getUser();

        if(!Objects.equals(user.getId(), authUser.getUserId())){
            throw new IllegalArgumentException("댓글 수정 권한이 업습니다.");
        }

        commentRepository.delete(comment);

        return ResponseEntity.ok("댓글이 성공적으로 삭제됐습니다.");
    }


    // 게시글 조회
    public Post searchPost(Long postId){
        return postRepository.findById(postId).orElseThrow(() -> new NullPointerException("해당 게시글이 없습니다."));
    }

    // Entity -> Dto 변환
    public List<CommentSearchResponseDto> commentDtoList(List<Comment> commentList){
        List<CommentSearchResponseDto> dtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            String postTitle = comment.getPost().getTitle();
            CommentSearchResponseDto dto = new CommentSearchResponseDto(postTitle, comment);
            dtoList.add(dto);
        }

        return dtoList;
    }
}
