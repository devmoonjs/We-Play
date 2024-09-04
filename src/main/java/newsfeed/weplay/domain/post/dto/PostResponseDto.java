package newsfeed.weplay.domain.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import newsfeed.weplay.domain.post.entity.Post;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private String city;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int likeCount;
    private int commentCount;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.userId = post.getUser().getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.city = post.getCity();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.likeCount = post.getLikeCount();
        this.commentCount = post.getCommentList().size();

    }
}