package newsfeed.weplay.domain.post.repository;

import newsfeed.weplay.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 뉴스피드 조회(최신순)
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
