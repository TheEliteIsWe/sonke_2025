package secret.credible.informantOrwhistleblower.app.controller;

import secret.credible.informantOrwhistleblower.app.model.Report;
import secret.credible.informantOrwhistleblower.app.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/inform")
    public String informForm() {
        return "inform";
    }

    @GetMapping("/whistleblow")
    public String whistleblowForm() {
        return "whistleblow";
    }

    @PostMapping("/submit-report")
    public String submitReport(@RequestParam("reportType") String reportType,
                              @RequestParam("description") String description,
                              @RequestParam(value = "organizationName", required = false) String organizationName,
                              @RequestParam(value = "documents", required = false) MultipartFile[] documents,
                              @RequestParam("idDocument") MultipartFile idDocument,
                              @RequestParam("addressProof") MultipartFile addressProof,
                              @RequestParam("incomeProof") MultipartFile incomeProof,
                              RedirectAttributes redirectAttributes) {
        
        try {
            Report report = new Report(reportType, description);
            
            if ("WHISTLEBLOW".equals(reportType)) {
                report.setOrganizationName(organizationName);
            }
            
            // Save supporting documents
            if (documents != null && documents.length > 0) {
                List<String> documentPaths = new ArrayList<>();
                for (MultipartFile doc : documents) {
                    if (!doc.isEmpty()) {
                        String path = reportService.saveFile(doc, "documents");
                        if (path != null) {
                            documentPaths.add(path);
                        }
                    }
                }
                report.setDocumentPaths(documentPaths);
            }
            
            // Save verification documents
            report.setIdDocumentPath(reportService.saveFile(idDocument, "id"));
            report.setAddressProofPath(reportService.saveFile(addressProof, "address"));
            report.setIncomeProofPath(reportService.saveFile(incomeProof, "income"));
            
            Report savedReport = reportService.saveReport(report);
            redirectAttributes.addFlashAttribute("message", 
                "Report submitted successfully. Reference ID: " + savedReport.getAnonymizedId());
            redirectAttributes.addFlashAttribute("referenceId", savedReport.getAnonymizedId());
            
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", 
                "Error processing files. Please try again.");
        }
        
        return "redirect:/success";
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("reports", reportService.getUnreviewedReports());
        model.addAttribute("allReports", reportService.getAllReports());
        return "admin/dashboard";
    }

    @GetMapping("/admin/report/{id}")
    public String viewReport(@PathVariable Long id, Model model) {
        Report report = reportService.getReportById(id);
        if (report != null) {
            model.addAttribute("report", report);
            return "admin/report-detail";
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/admin/report/{id}/review")
    public String markAsReviewed(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Report report = reportService.markAsReviewed(id);
        if (report != null) {
            redirectAttributes.addFlashAttribute("message", 
                "Report " + report.getAnonymizedId() + " marked as reviewed.");
        }
        return "redirect:/admin/dashboard";
    }
}

