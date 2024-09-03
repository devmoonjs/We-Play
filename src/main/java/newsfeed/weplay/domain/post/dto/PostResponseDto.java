package newsfeed.weplay.domain.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDto {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private String city;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
