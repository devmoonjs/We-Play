package newsfeed.weplay.post.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 게시물 ID

    @Column(nullable = false)
    private Long userId; // 작성자 ID

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
}
