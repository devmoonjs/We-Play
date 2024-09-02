package newsfeed.weplay.domain.comment.service;

import lombok.RequiredArgsConstructor;
import newsfeed.weplay.domain.comment.dto.request.CommentRequestDto;
import newsfeed.weplay.domain.comment.dto.response.CommentSaveResponseDto;
import newsfeed.weplay.domain.comment.repository.CommentRepository;
import newsfeed.weplay.domain.comment.entity.Comment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

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

    public List<CommentSaveResponseDto> searchPostComment(Long postId) {
        // 포스트 별 전체 댓글 조회
    }
}
