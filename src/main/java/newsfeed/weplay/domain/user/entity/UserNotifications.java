package newsfeed.weplay.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import newsfeed.weplay.domain.common.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
public class UserNotifications extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "is_notification_read")
    private boolean isNotificationRead = false;

    public UserNotifications(User taggedUser, Long commentId) {
        this.user = taggedUser;
        this.commentId = commentId;
    }

}
