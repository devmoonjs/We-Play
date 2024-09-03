package newsfeed.weplay.domain.comment.dto.request;

import lombok.Getter;

@Getter
public class CommentUpdateRequestDto {
    private String content;
    private Long userId;
    private String password;
}
