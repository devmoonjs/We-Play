package newsfeed.weplay.domain.user.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import newsfeed.weplay.domain.user.entity.User;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {
    private Long id;

    private String email;

    private String username;

    private String imgUrl;

    private String location;

    private Integer age;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.imgUrl = user.getImgUrl();
        this.location = user.getLocation();
        this.age = user.getAge();
    }
}
