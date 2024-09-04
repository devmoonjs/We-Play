package newsfeed.weplay.domain.comment.repository;

import newsfeed.weplay.domain.comment.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
