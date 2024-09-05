package newsfeed.weplay.domain.friend.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import newsfeed.weplay.domain.friend.entity.FriendStatusEnum;
import newsfeed.weplay.domain.user.entity.User;

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

    public static FriendSimpleResponseDto sendDto(User user, FriendStatusEnum status) {
        return new FriendSimpleResponseDto(
                user.getId(),
                user.getUsername(),
                status
        );
    }
}
