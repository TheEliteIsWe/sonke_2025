package secret.credible.informantOrwhistleblower.app.service;

import secret.credible.informantOrwhistleblower.app.model.Report;
import secret.credible.informantOrwhistleblower.app.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ReportService {
    
    @Autowired
    private ReportRepository reportRepository;
    
    private final String uploadDir = "uploads/";
    
    public Report saveReport(Report report) {
        // Generate anonymized ID for privacy protection
        report.setAnonymizedId("ANM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        return reportRepository.save(report);
    }
    
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }
    
    public List<Report> getUnreviewedReports() {
        return reportRepository.findByReviewed(false);
    }
    
    public Report getReportById(Long id) {
        return reportRepository.findById(id).orElse(null);
    }
    
    public Report markAsReviewed(Long id) {
        Report report = getReportById(id);
        if (report != null) {
            report.setReviewed(true);
            return reportRepository.save(report);
        }
        return null;
    }
    
    public String saveFile(MultipartFile file, String category) throws IOException {
        if (file.isEmpty()) {
            return null;
        }
        
        // Create upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir + category);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        // Generate unique filename
        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(filename);
        
        // Save file
        Files.copy(file.getInputStream(), filePath);
        
        return category + "/" + filename;
    }
    
    public void deleteReport(Long id) {
        reportRepository.deleteById(id);
    }
}

