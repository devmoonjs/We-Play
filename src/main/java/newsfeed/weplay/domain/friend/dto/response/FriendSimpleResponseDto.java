package newsfeed.weplay.domain.friend.dto.response;

import lombok.Getter;
import newsfeed.weplay.domain.friend.entity.FriendStatusEnum;

@Getter
public class FriendSimpleResponseDto {
    private Long userId;
    private String userName;
    private FriendStatusEnum status;

    public FriendSimpleResponseDto(Long userId, String userName, FriendStatusEnum status) {
        this.userId = userId;
        this.userName = userName;
        this.status = status;
    }
}
