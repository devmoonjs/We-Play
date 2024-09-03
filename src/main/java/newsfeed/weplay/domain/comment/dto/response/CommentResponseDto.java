package newsfeed.weplay.domain.comment.dto.response;

import lombok.Getter;
import newsfeed.weplay.domain.comment.entity.Comment;
import newsfeed.weplay.domain.like.entity.Likes;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CommentResponseDto {
    private final Long id;
    private final String content;
    private final String userName;
    private final List<Likes> like;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CommentResponseDto(Comment comment, List<Likes> like){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.userName = comment.getUser().getUsername();
        this.like = like;
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }
}


