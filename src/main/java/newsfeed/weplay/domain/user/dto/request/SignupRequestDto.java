package newsfeed.weplay.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class SignupRequestDto {
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "비밀번호는 최소 8자 이상, 영문 대소문자, 숫자, 특수문자를 포함해야 합니다."
    )
    private String password;

    @NotBlank(message = "공백은 사용할 수 없습니다.")
    private String username;

    private String imgUrl;

    private String location;

    @Positive(message = "양수만 입력 가능합니다.")
    private Integer age;

    public void encodedPassword(String password) {
        this.password = password;
    }
}
