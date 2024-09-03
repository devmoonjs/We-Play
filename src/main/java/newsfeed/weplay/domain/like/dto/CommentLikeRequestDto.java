package newsfeed.weplay.domain.like.dto;

import lombok.Getter;

@Getter
public class CommentLikeRequestDto {

    private Long userId;
    private Long commentId;
}
