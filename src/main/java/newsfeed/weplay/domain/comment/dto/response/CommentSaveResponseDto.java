package newsfeed.weplay.domain.comment.dto.response;

import lombok.Getter;
import newsfeed.weplay.domain.comment.entity.Comment;

import java.time.LocalDateTime;@Getter
public class CommentSaveResponseDto {
    private final Long id;
    private final String content;
    private final String userName;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CommentSaveResponseDto(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.userName = comment.getUser().getUserName();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }
}


