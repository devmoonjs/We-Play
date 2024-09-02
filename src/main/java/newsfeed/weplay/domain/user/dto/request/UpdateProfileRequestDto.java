package newsfeed.weplay.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class UpdateProfileRequestDto {
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    private String password;

    private String newPassword;

    @NotBlank(message = "공백은 사용할 수 없습니다.")
    private String username;

    private String imgUrl;

    private String location;

    @Positive(message = "양수만 입력 가능합니다.")
    private Integer age;
}
