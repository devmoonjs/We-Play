package newsfeed.weplay.domain.user.dto.request;

import lombok.Getter;

@Getter
public class DeleteUserRequestDto {
    private String email;
    private String password;
}
