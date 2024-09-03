package newsfeed.weplay.domain.like.dto;

import lombok.Getter;

@Getter
public class PostLikeRequestDto {

    private Long userId;
    private Long postId;
}
