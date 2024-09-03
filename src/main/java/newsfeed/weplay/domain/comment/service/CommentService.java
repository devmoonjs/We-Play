package newsfeed.weplay.domain.comment.service;

import lombok.RequiredArgsConstructor;
import newsfeed.weplay.domain.comment.dto.request.CommentRequestDto;
import newsfeed.weplay.domain.comment.dto.request.CommentUpdateRequestDto;
import newsfeed.weplay.domain.comment.dto.response.CommentResponseDto;
import newsfeed.weplay.domain.comment.dto.response.CommentSaveResponseDto;
import newsfeed.weplay.domain.comment.repository.CommentRepository;
import newsfeed.weplay.domain.comment.entity.Comment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public CommentSaveResponseDto postComment(Long planId, CommentRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new NullPointerException("해당 유저가 없습니다."));

        Post post = postRepository.findById(planId);

        Comment comment = new Comment(requestDto, user, post);

        Comment saveComment = commentRepository.save(comment);

        return CommentSaveResponseDto(saveComment);
    }

    public List<CommentResponseDto> searchPostComment(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NullPointerException("해당 게시글이 없습니다."));

        List<Comment> commentList = commentRepository.findByPostIdWithUser(postId);

        List<CommentResponseDto> dtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            List<Like> like = comment.getLike();
            CommentResponseDto dto = new CommentResponseDto(comment, like);
            dtoList.add(dto);
        }

        return dtoList;
    }

    public List<CommentResponseDto> searchUserComment(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NullPointerException("해당 유저가 없습니다."));

        List<Comment> commentList = user.getComment();

        List<CommentResponseDto> dtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            List<Like> like = comment.getLike();
            CommentResponseDto dto = new CommentResponseDto(comment, like);
            dtoList.add(dto);
        }

        return dtoList;
    }

    @Transactional
    public CommentResponseDto updateComment(Long id, CommentUpdateRequestDto requestDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("해당 댓글이 없습니다."));

        User user = userRepository.findBtId(requestDto.getUserId());

        if(comment.getUser().getId() != user.getId() || comment.getUser().getPassword() != user.getPassword()){
            throw new IllegalArgumentException("댓글 수정 권한이 업습니다.");
        }

        comment.update(requestDto.getContent());

        return new CommentResponseDto(comment);
    }

    @Transactional
    public ResponseEntity<String> deleteComment(Long id, CommentUpdateRequestDto requestDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("해당 댓글이 없습니다."));

        User user = comment.getUser();

        if(user.getId() != requestDto.getUserId() || user.getPassword() != requestDto.getPassword()){
            throw new IllegalArgumentException("댓글 수정 권한이 업습니다.");
        }

        commentRepository.delete(comment);

        return ResponseEntity.ok("댓글이 성공적으로 삭제됐습니다.");
    }
}
