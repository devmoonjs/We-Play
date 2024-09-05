package newsfeed.weplay.domain.friend.dto.response;

import lombok.Getter;
import newsfeed.weplay.domain.friend.entity.FriendStatusEnum;

@Getter
public class FriendSimpleResponseDto {
    private final Long userId;
    private final String userName;
    private final FriendStatusEnum status;

    public FriendSimpleResponseDto(Long userId, String userName, FriendStatusEnum status) {
        this.userId = userId;
        this.userName = userName;
        this.status = status;
    }
}
