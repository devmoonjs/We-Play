package newsfeed.weplay.domain.comment.dto.response;

import lombok.Getter;
import newsfeed.weplay.domain.comment.entity.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private final Long id;
    private final String content;
    private final String userName;
    private final List<Like> like;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CommentResponseDto(Comment comment, List<Lzike> like){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.userName = comment.getUser().getUserName();
        this.like = like;
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }
}


