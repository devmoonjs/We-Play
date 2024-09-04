package newsfeed.weplay.domain.comment.dto.request;

import lombok.Getter;

@Getter
public class CommentSaveRequestDto {
    private String content;
    private Long[] taggedUserIdList = new Long[0];
}
