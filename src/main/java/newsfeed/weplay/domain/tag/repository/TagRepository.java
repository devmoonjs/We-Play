package newsfeed.weplay.domain.tag.repository;

import newsfeed.weplay.domain.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
