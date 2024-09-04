package newsfeed.weplay.domain.user.repository;

import newsfeed.weplay.domain.user.entity.UserNotifications;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserNotificationsRepository extends JpaRepository<UserNotifications, Long> {
    List<UserNotifications> findByUserIdAndIsNotificationReadFalse(Long userId);
}
