package newsfeed.weplay.domain.auth.dto;

import lombok.Getter;

@Getter
public class AuthUser {

    private final String username;
    private final String email;

    public AuthUser(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
