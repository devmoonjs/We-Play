package newsfeed.weplay.domain.friend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import newsfeed.weplay.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor
public class Friend {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_user_id")
    private User friendUser;

    @Enumerated(value = EnumType.STRING)
    private FriendStatusEnum friendStatus;

    public Friend(User user, User friendUser) {
        this.user = user;
        this.friendUser = friendUser;
        this.friendStatus = FriendStatusEnum.REQUESTING;
    }

    public void changeFriendStatus(FriendStatusEnum statusEnum) {
        this.friendStatus = statusEnum;
    }
}
