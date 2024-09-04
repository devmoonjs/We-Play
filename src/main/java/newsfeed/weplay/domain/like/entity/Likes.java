package newsfeed.weplay.domain.like.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import newsfeed.weplay.domain.comment.entity.Comment;
import newsfeed.weplay.domain.common.entity.BaseEntity;
import newsfeed.weplay.domain.post.entity.Post;
import newsfeed.weplay.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor
public class Likes extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public Likes(User user, Post post){
        this.user = user;
        this.post = post;
    }

    public Likes(User user, Comment comment){
        this.user = user;
        this.comment = comment;
    }
}
