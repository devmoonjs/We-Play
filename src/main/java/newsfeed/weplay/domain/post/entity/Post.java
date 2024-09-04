package newsfeed.weplay.domain.post.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import newsfeed.weplay.domain.comment.entity.Comment;
import newsfeed.weplay.domain.common.entity.BaseEntity;
import newsfeed.weplay.domain.like.entity.Likes;
import newsfeed.weplay.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor
public class Post extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 작성자 정보

    @Column(nullable = false)
    private String title; // 게시물 제목

    @Column(nullable = false)
    private String content; // 게시물 내용

    @Column(nullable = false)
    private String city; // 지역

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likes = new ArrayList<>(); //좋아요 연결

    @Column(name = "likes", nullable = false)
    private int likeCount = 0; //좋아요 수 세기

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>(); // 댓글 연결

    @Column(name = "comments", nullable = false)
    private int commentCount = 0; //좋아요 수 세기

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

    public void setCommentsList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public void update(Post updatedPost) {
        this.title = updatedPost.getTitle();
        this.content = updatedPost.getContent();
        this.city = updatedPost.getCity();
        this.updatedAt = LocalDateTime.now();
    }

    public void increaseLikeCount(){
        this.likeCount++; //좋아요 증가
    }

    public void decreaseLikeCount(){
        this.likeCount--; //좋아요 감소
    }
}
