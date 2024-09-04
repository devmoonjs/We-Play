package newsfeed.weplay.domain.comment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import newsfeed.weplay.domain.comment.dto.request.CommentRequestDto;
import newsfeed.weplay.domain.comment.dto.request.CommentSaveRequestDto;
import newsfeed.weplay.domain.common.entity.BaseEntity;
import newsfeed.weplay.domain.like.entity.Likes;
import newsfeed.weplay.domain.post.entity.Post;
import newsfeed.weplay.domain.tag.entity.Tag;
import newsfeed.weplay.domain.user.entity.User;
import newsfeed.weplay.domain.user.entity.UserNotifications;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likes = new ArrayList<>(); //좋아요 연결

    @Column(name = "likes", nullable = false)
    private int likeCount = 0; //좋아요 수 세기

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tag> tagList = new ArrayList<>();

    public Comment(CommentSaveRequestDto requestDto, User user, Post post) {
        this.content = requestDto.getContent();
        this.user = user;
        this.post = post;
    }

    public void update(String content) {
        this.content = content;
    }

    public void increaseLikeCount(){
        this.likeCount++; //좋아요 증가
    }

    public void decreaseLikeCount(){
        this.likeCount--; //좋아요 감소
    }
}
