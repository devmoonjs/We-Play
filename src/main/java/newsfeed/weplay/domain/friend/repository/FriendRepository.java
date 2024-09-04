package newsfeed.weplay.domain.friend.repository;

import newsfeed.weplay.domain.friend.entity.Friend;
import newsfeed.weplay.domain.friend.entity.FriendStatusEnum;
import newsfeed.weplay.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findFriendsByFriendUser(User user);

    List<Friend> findFriendsByUserAndFriendStatus(User user, FriendStatusEnum friendStatusEnum);

//    List<> findFriendUserByUserAndFriendStatus(User user, FriendStatusEnum friendStatusEnum);

    void deleteFriendByFriendUserAndUser(User friendUser, User user);
}
