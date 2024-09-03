package newsfeed.weplay.domain.post.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import newsfeed.weplay.domain.like.entity.Likes;
import newsfeed.weplay.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 게시물 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId; // 작성자 ID

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

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Likes> likes = new ArrayList<>(); //좋아요 연결

    @Column(name = "likes", nullable = false)
    private int likeCount=0; //좋아요수 세기위한 컬럼

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void increaseLikeCount(){
        this.likeCount++; //좋아요 증가
    }

    public void decreaseLikeCount(){
        this.likeCount--; //좋아요 감소
    }
}
