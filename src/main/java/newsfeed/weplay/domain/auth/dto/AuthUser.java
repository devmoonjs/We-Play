package newsfeed.weplay.domain.auth.dto;

import lombok.Getter;

@Getter
public class AuthUser {

    private Long userId;
    private final String username;
    private final String email;

    public AuthUser(Long userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
    }
}
