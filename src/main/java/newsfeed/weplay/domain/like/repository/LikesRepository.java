package newsfeed.weplay.domain.like.repository;

import newsfeed.weplay.domain.comment.entity.Comment;
import newsfeed.weplay.domain.like.entity.Likes;
import newsfeed.weplay.domain.post.entity.Post;
import newsfeed.weplay.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes,Long> {
    Optional<Likes> findByUserAndPost(User user, Post post);
    Optional<Likes> findByUserAndComment(User user , Comment comment);
}
