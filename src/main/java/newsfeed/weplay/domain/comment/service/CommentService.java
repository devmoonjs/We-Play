package newsfeed.weplay.domain.comment.service;

import lombok.RequiredArgsConstructor;
import newsfeed.weplay.domain.auth.dto.AuthUser;
import newsfeed.weplay.domain.comment.dto.request.CommentRequestDto;
import newsfeed.weplay.domain.comment.dto.request.CommentSaveRequestDto;
import newsfeed.weplay.domain.comment.dto.response.CommentResponseDto;
import newsfeed.weplay.domain.comment.dto.response.CommentSaveResponseDto;
import newsfeed.weplay.domain.comment.dto.response.CommentSearchResponseDto;
import newsfeed.weplay.domain.comment.entity.Comment;
import newsfeed.weplay.domain.comment.entity.Report;
import newsfeed.weplay.domain.comment.repository.CommentRepository;
import newsfeed.weplay.domain.comment.repository.ReportRepository;
import newsfeed.weplay.domain.post.entity.Post;
import newsfeed.weplay.domain.post.repository.PostRepository;
import newsfeed.weplay.domain.tag.entity.Tag;
import newsfeed.weplay.domain.tag.repository.TagRepository;
import newsfeed.weplay.domain.user.entity.User;
import newsfeed.weplay.domain.user.entity.UserNotifications;
import newsfeed.weplay.domain.user.repository.UserNotificationsRepository;
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
    private final TagRepository tagRepository;
    private final UserNotificationsRepository userNotificationsRepository;
    private final ReportRepository reportRepository;
    private final ReportService reportService;


    @Transactional
    public CommentSaveResponseDto postComment(Long postId, CommentSaveRequestDto requestDto, AuthUser authUser) {

        User user = userRepository.findById(authUser.getUserId()).orElseThrow();

        Post post = searchPost(postId);

        Comment comment = new Comment(requestDto, user, post);

        Comment saveComment = commentRepository.save(comment);

        // 태그 생성, 유저 알림 생성
        if(requestDto.getTaggedUserIdList().length != 0) {
            for (Long userId : requestDto.getTaggedUserIdList()) {
                User taggedUser = userRepository.findById(userId).orElseThrow();

                // 태그 생성
                Tag tag = new Tag(user, taggedUser, comment);
                tagRepository.save(tag);

                // 유저 알림 생성
                UserNotifications userNotifications = new UserNotifications(taggedUser, saveComment.getId());
                userNotificationsRepository.save(userNotifications);
            }
        }
        post.increaseCommentCount();


        return new CommentSaveResponseDto(saveComment);
    }

    public List<CommentSearchResponseDto> searchPostComment(Long postId) {
        Post post = searchPost(postId);
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
        Comment comment = searchComment(commentId);

        User user = userRepository.findById(authUser.getUserId()).orElseThrow();

        if(!Objects.equals(comment.getUser().getId(), user.getId())){
            throw new IllegalArgumentException("댓글 수정 권한이 업습니다.");
        }

        comment.update(requestDto.getContent());

        return new CommentResponseDto(comment);
    }

    @Transactional
    public ResponseEntity<String> deleteComment(Long commentId, AuthUser authUser) {
        Comment comment = searchComment(commentId);

        Post post = searchPost(comment.getPost().getId());

        User user = comment.getUser();

        if(!Objects.equals(user.getId(), authUser.getUserId())){
            return ResponseEntity.badRequest().body("댓글 수정 권한이 업습니다.");
        }

        commentRepository.delete(comment);

        post.decraseCommentCount();

        return ResponseEntity.ok("댓글이 성공적으로 삭제됐습니다.");
    }

    @Transactional
    public ResponseEntity<String> reportComment(Long commentId, AuthUser authUser, CommentRequestDto requestDto) {
        Comment comment = searchComment(commentId);

        User user = userRepository.findById(authUser.getUserId()).orElseThrow();

        List<Report> reportList = comment.getReportList();

        if(reportList != null){
            for (Report report : reportList) {
                if(report.getUser().getId().equals(authUser.getUserId())){
                    return ResponseEntity.badRequest().body("이미 해당 댓글을 신고하셨습니다.");
                }
            }
        }

        Report report = new Report(requestDto.getContent(), user, comment);

        reportRepository.save(report);

        comment.increaseReportCount();

        // Lazy로 인해 메서드 종료 시 까지 List크기가 반영이 안되므로 강제 추가
        comment.getReportList().add(report);

        if(comment.getReportList().size() >= 3){
            reportService.commentBlind(comment);
            return ResponseEntity.ok("신고 누적으로 해당 댓글이 블라인드 처리됐습니다.");
        }else{
            return ResponseEntity.ok("댓글 신고 완료.");
        }
    }

    // 댓글 조회
    public Comment searchComment(Long commentId){
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NullPointerException("해당 댓글이 없습니다."));
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
