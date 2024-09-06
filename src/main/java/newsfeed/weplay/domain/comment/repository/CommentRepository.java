package newsfeed.weplay.domain.comment.repository;

import newsfeed.weplay.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
