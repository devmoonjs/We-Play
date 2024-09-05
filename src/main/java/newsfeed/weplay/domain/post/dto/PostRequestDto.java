package newsfeed.weplay.domain.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class PostRequestDto {
    private String title;
    private String content;
    private String city;
}
