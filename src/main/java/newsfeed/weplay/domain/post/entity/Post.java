package newsfeed.weplay.domain.post.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import newsfeed.weplay.domain.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 게시물 ID

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 작성자 정보

    @Column(nullable = false)
    private String title; // 게시물 제목

    @Column(nullable = false)
    private String content; // 게시물 내용

    @Column(nullable = false)
    private String city; // 지역

    @Column(nullable = false)
    private LocalDateTime createdAt; // 게시물 생성 일시

    @Column(nullable = false)
    private LocalDateTime updatedAt; // 게시물 수정 일시

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void update(Post updatedPost) {
        this.title = updatedPost.getTitle();
        this.content = updatedPost.getContent();
        this.city = updatedPost.getCity();
        this.updatedAt = LocalDateTime.now();
    }
}
