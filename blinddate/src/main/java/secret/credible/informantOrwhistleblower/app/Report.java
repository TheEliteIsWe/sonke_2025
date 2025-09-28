package secret.credible.informantOrwhistleblower.app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "report_type")
    private String reportType; // "INFORM" or "WHISTLEBLOW"
    
    @Column(name = "organization_name")
    private String organizationName; // Only for whistleblowing
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "reviewed")
    private boolean reviewed = false;
    
    @Column(name = "anonymized_id")
    private String anonymizedId; // For protecting identity
    
    // File paths
    @ElementCollection
    @CollectionTable(name = "report_documents", joinColumns = @JoinColumn(name = "report_id"))
    @Column(name = "document_path")
    private List<String> documentPaths;
    
    @Column(name = "id_document_path")
    private String idDocumentPath;
    
    @Column(name = "address_proof_path")
    private String addressProofPath;
    
    @Column(name = "income_proof_path")
    private String incomeProofPath;
    
    // Constructors
    public Report() {
        this.createdAt = LocalDateTime.now();
    }
    
    public Report(String reportType, String description) {
        this();
        this.reportType = reportType;
        this.description = description;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getReportType() { return reportType; }
    public void setReportType(String reportType) { this.reportType = reportType; }
    
    public String getOrganizationName() { return organizationName; }
    public void setOrganizationName(String organizationName) { this.organizationName = organizationName; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public boolean isReviewed() { return reviewed; }
    public void setReviewed(boolean reviewed) { this.reviewed = reviewed; }
    
    public String getAnonymizedId() { return anonymizedId; }
    public void setAnonymizedId(String anonymizedId) { this.anonymizedId = anonymizedId; }
    
    public List<String> getDocumentPaths() { return documentPaths; }
    public void setDocumentPaths(List<String> documentPaths) { this.documentPaths = documentPaths; }
    
    public String getIdDocumentPath() { return idDocumentPath; }
    public void setIdDocumentPath(String idDocumentPath) { this.idDocumentPath = idDocumentPath; }
    
    public String getAddressProofPath() { return addressProofPath; }
    public void setAddressProofPath(String addressProofPath) { this.addressProofPath = addressProofPath; }
    
    public String getIncomeProofPath() { return incomeProofPath; }
    public void setIncomeProofPath(String incomeProofPath) { this.incomeProofPath = incomeProofPath; }
}

