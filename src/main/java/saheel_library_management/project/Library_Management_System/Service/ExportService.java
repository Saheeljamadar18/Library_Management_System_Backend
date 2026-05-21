package saheel_library_management.project.Library_Management_System.Service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import saheel_library_management.project.Library_Management_System.Entity.Books;
import saheel_library_management.project.Library_Management_System.Entity.Student;
import saheel_library_management.project.Library_Management_System.Entity.Transaction;
import saheel_library_management.project.Library_Management_System.Repository.BookRepository;
import saheel_library_management.project.Library_Management_System.Repository.StudentRepository;
import saheel_library_management.project.Library_Management_System.Repository.TransactionRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ExportService {

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Value("${aws.s3.bucket:library-management-exports}")
    private String bucketName;

    // ----- CSV Generation -----

    public byte[] exportStudentsToCsv() throws Exception {
        List<Student> students = studentRepository.findAll();
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             CSVWriter writer = new CSVWriter(new OutputStreamWriter(baos))) {
            writer.writeNext(new String[]{"ID", "Name", "Email", "Dept", "Sem", "Gender", "Mobile"});
            for (Student s : students) {
                writer.writeNext(new String[]{
                        String.valueOf(s.getId()), s.getName(), s.getEmail(), s.getDept(),
                        s.getSem(), s.getGender(), s.getMobile()
                });
            }
            writer.flush();
            return baos.toByteArray();
        }
    }

    public byte[] exportBooksToCsv() throws Exception {
        List<Books> books = bookRepository.findAll();
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             CSVWriter writer = new CSVWriter(new OutputStreamWriter(baos))) {
            writer.writeNext(new String[]{"ID", "Title", "Publisher", "Category", "Rack", "Available", "Author"});
            for (Books b : books) {
                writer.writeNext(new String[]{
                        String.valueOf(b.getBook_id()), b.getTitle(), b.getPublisherName(),
                        b.getCategory().name(), String.valueOf(b.getRackNo()),
                        String.valueOf(b.isAvailability()),
                        b.getAuthor() != null ? b.getAuthor().getName() : ""
                });
            }
            writer.flush();
            return baos.toByteArray();
        }
    }

    public byte[] exportTransactionsToCsv() throws Exception {
        List<Transaction> transactions = transactionRepository.findAll();
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             CSVWriter writer = new CSVWriter(new OutputStreamWriter(baos))) {
            writer.writeNext(new String[]{"ID", "Type", "Transaction Date", "Due Date", "Book ID", "Card ID"});
            for (Transaction t : transactions) {
                writer.writeNext(new String[]{
                        String.valueOf(t.getId()),
                        t.getTransactionType() != null ? t.getTransactionType().name() : "",
                        t.getTransactiondate() != null ? String.valueOf(t.getTransactiondate()) : "",
                        t.getDueDate() != null ? t.getDueDate() : "",
                        t.getBook() != null ? String.valueOf(t.getBook().getBook_id()) : "",
                        t.getCard() != null ? String.valueOf(t.getCard().getId()) : ""
                });
            }
            writer.flush();
            return baos.toByteArray();
        }
    }

    // ----- PDF Generation -----

    public byte[] exportStudentsToPdf() throws Exception {
        List<Student> students = studentRepository.findAll();
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();
            document.add(new Paragraph("Students List"));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.addCell("ID"); table.addCell("Name"); table.addCell("Email");
            table.addCell("Dept"); table.addCell("Sem"); table.addCell("Gender"); table.addCell("Mobile");

            for (Student s : students) {
                table.addCell(String.valueOf(s.getId())); table.addCell(s.getName()); table.addCell(s.getEmail());
                table.addCell(s.getDept()); table.addCell(s.getSem()); table.addCell(s.getGender()); table.addCell(s.getMobile());
            }
            document.add(table);
            document.close();
            return baos.toByteArray();
        }
    }

    public byte[] exportBooksToPdf() throws Exception {
        List<Books> books = bookRepository.findAll();
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();
            document.add(new Paragraph("Books List"));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.addCell("ID"); table.addCell("Title"); table.addCell("Publisher");
            table.addCell("Category"); table.addCell("Rack"); table.addCell("Available"); table.addCell("Author");

            for (Books b : books) {
                table.addCell(String.valueOf(b.getBook_id())); table.addCell(b.getTitle()); table.addCell(b.getPublisherName());
                table.addCell(b.getCategory().name()); table.addCell(String.valueOf(b.getRackNo())); table.addCell(String.valueOf(b.isAvailability()));
                table.addCell(b.getAuthor() != null ? b.getAuthor().getName() : "");
            }
            document.add(table);
            document.close();
            return baos.toByteArray();
        }
    }

    public byte[] exportTransactionsToPdf() throws Exception {
        List<Transaction> transactions = transactionRepository.findAll();
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();
            document.add(new Paragraph("Transactions List"));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.addCell("ID"); table.addCell("Type"); table.addCell("Transaction Date");
            table.addCell("Due Date"); table.addCell("Book ID"); table.addCell("Card ID");

            for (Transaction t : transactions) {
                table.addCell(String.valueOf(t.getId()));
                table.addCell(t.getTransactionType() != null ? t.getTransactionType().name() : "");
                table.addCell(t.getTransactiondate() != null ? String.valueOf(t.getTransactiondate()) : "");
                table.addCell(t.getDueDate() != null ? t.getDueDate() : "");
                table.addCell(t.getBook() != null ? String.valueOf(t.getBook().getBook_id()) : "");
                table.addCell(t.getCard() != null ? String.valueOf(t.getCard().getId()) : "");
            }
            document.add(table);
            document.close();
            return baos.toByteArray();
        }
    }

    // ----- S3 Upload -----

    public String uploadToS3AndGetUrl(byte[] data, String fileName, String contentType) {
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
        
        // Ensure bucket exists. If not, create it. (In production, usually pre-created)
        if (!s3Client.doesBucketExistV2(bucketName)) {
            s3Client.createBucket(bucketName);
        }

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(data.length);
        metadata.setContentType(contentType);

        s3Client.putObject(new PutObjectRequest(bucketName, uniqueFileName, new ByteArrayInputStream(data), metadata));

        // Generate Presigned URL valid for 1 hour
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60;
        expiration.setTime(expTimeMillis);

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, uniqueFileName)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);

        URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }
}
