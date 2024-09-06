package newsfeed.weplay.domain.comment.service;

import lombok.RequiredArgsConstructor;
import newsfeed.weplay.domain.comment.entity.Comment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportService {

    @Transactional
    public void commentBlind(Comment comment){
        comment.update("신고 누적으로 블라인드 처리된 댓글");
    }
}
