package newsfeed.weplay.domain.comment.dto.request;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String content;
    private Long userId;
}
