package newsfeed.weplay.domain.friend.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class FriendSendDto {
    private final List<FriendSimpleResponseDto> requestList;
    private final List<FriendSimpleResponseDto> responseList;

    public FriendSendDto(List<FriendSimpleResponseDto> requestList, List<FriendSimpleResponseDto> responseList) {
        this.requestList = requestList;
        this.responseList = responseList;
    }
}
