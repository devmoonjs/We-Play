package newsfeed.weplay.domain.tag.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import newsfeed.weplay.domain.comment.entity.Comment;
import newsfeed.weplay.domain.common.entity.BaseEntity;
import newsfeed.weplay.domain.user.entity.User;

import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor
public class Tag extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tagged_user_id")
    private User taggedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public Tag(User user, User taggedUser, Comment comment) {
        this.user = user;
        this.taggedUser = taggedUser;
        this.comment = comment;
    }
}
