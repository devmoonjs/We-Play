package newsfeed.weplay.domain.like.repository;

import newsfeed.weplay.domain.like.entity.PostLike;
import newsfeed.weplay.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike,Long> {
    Optional<PostLike> findByUserAndPost(User user , Post post);
}
