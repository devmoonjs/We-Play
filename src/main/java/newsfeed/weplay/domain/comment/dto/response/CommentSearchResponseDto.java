package newsfeed.weplay.domain.comment.dto.response;

import lombok.Getter;
import newsfeed.weplay.domain.comment.entity.Comment;

@Getter
public class CommentSearchResponseDto {
    private final Long id;
    private final String postTitle;
    private final String content;
    private final String userName;
    private final int likeCount;
    private final int reportCount;

    public CommentSearchResponseDto(String postTitle, Comment comment){
        this.id = comment.getId();
        this.postTitle = postTitle;
        this.content = comment.getContent();
        this.userName = comment.getUser().getUsername();
        this.likeCount = comment.getLikeCount();
        this.reportCount = comment.getReportList().size();
    }
}


