package saheel_library_management.project.Library_Management_System.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import saheel_library_management.project.Library_Management_System.Service.ExportService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/export/apis")
public class ExportController {

    @Autowired
    private ExportService exportService;

    @GetMapping("/{entity}")
    public ResponseEntity<Map<String, String>> exportData(@PathVariable String entity, @RequestParam String format) {
        try {
            byte[] data;
            String fileName;
            String contentType;

            if ("students".equalsIgnoreCase(entity)) {
                if ("csv".equalsIgnoreCase(format)) {
                    data = exportService.exportStudentsToCsv();
                    fileName = "students.csv";
                    contentType = "text/csv";
                } else if ("pdf".equalsIgnoreCase(format)) {
                    data = exportService.exportStudentsToPdf();
                    fileName = "students.pdf";
                    contentType = "application/pdf";
                } else {
                    return ResponseEntity.badRequest().body(Map.of("error", "Unsupported format"));
                }
            } else if ("books".equalsIgnoreCase(entity)) {
                if ("csv".equalsIgnoreCase(format)) {
                    data = exportService.exportBooksToCsv();
                    fileName = "books.csv";
                    contentType = "text/csv";
                } else if ("pdf".equalsIgnoreCase(format)) {
                    data = exportService.exportBooksToPdf();
                    fileName = "books.pdf";
                    contentType = "application/pdf";
                } else {
                    return ResponseEntity.badRequest().body(Map.of("error", "Unsupported format"));
                }
            } else if ("transactions".equalsIgnoreCase(entity)) {
                if ("csv".equalsIgnoreCase(format)) {
                    data = exportService.exportTransactionsToCsv();
                    fileName = "transactions.csv";
                    contentType = "text/csv";
                } else if ("pdf".equalsIgnoreCase(format)) {
                    data = exportService.exportTransactionsToPdf();
                    fileName = "transactions.pdf";
                    contentType = "application/pdf";
                } else {
                    return ResponseEntity.badRequest().body(Map.of("error", "Unsupported format"));
                }
            } else {
                return ResponseEntity.badRequest().body(Map.of("error", "Unsupported entity"));
            }

            String s3Url = exportService.uploadToS3AndGetUrl(data, fileName, contentType);
            Map<String, String> response = new HashMap<>();
            response.put("url", s3Url);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("error", "Export failed: " + e.getMessage()));
        }
    }
}
