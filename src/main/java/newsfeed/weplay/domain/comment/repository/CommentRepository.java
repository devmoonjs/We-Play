package newsfeed.weplay.domain.comment.repository;

import newsfeed.weplay.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c JOIN FETCH c.user WHERE c.post.id = :postId")
    List<Comment> findByPostIdWithUser(Long postId);
}
