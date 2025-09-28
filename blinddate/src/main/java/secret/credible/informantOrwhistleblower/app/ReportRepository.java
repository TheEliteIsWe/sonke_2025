package secret.credible.informantOrwhistleblower.app.repository;

import secret.credible.informantOrwhistleblower.app.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByReviewed(boolean reviewed);
    List<Report> findByReportType(String reportType);
    List<Report> findByReportTypeAndReviewed(String reportType, boolean reviewed);
}

