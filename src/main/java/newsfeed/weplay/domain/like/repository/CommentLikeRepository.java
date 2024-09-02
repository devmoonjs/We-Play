package newsfeed.weplay.domain.like.repository;

import newsfeed.weplay.domain.like.entity.CommentLike;
import newsfeed.weplay.domain.like.entity.PostLike;
import newsfeed.weplay.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<PostLike> findByUserAndComment(User user , Comment comment);
}
