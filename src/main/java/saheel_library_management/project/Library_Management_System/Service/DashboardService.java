package saheel_library_management.project.Library_Management_System.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saheel_library_management.project.Library_Management_System.Enums.CardStatus;
import saheel_library_management.project.Library_Management_System.Repository.AuthorRepository;
import saheel_library_management.project.Library_Management_System.Repository.BookRepository;
import saheel_library_management.project.Library_Management_System.Repository.CardRepository;
import saheel_library_management.project.Library_Management_System.Repository.StudentRepository;
import saheel_library_management.project.Library_Management_System.Repository.TransactionRepository;
import saheel_library_management.project.Library_Management_System.response.DashboardStatsDto;

@Service
public class DashboardService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    CardRepository cardRepository;

    public DashboardStatsDto getStats() {
        long totalBooks = bookRepository.count();
        long availableBooks = bookRepository.countByAvailability(true);

        DashboardStatsDto stats = new DashboardStatsDto();
        stats.setTotalStudents(studentRepository.count());
        stats.setTotalAuthors(authorRepository.count());
        stats.setTotalBooks(totalBooks);
        stats.setAvailableBooks(availableBooks);
        stats.setBorrowedBooks(totalBooks - availableBooks);
        stats.setTotalTransactions(transactionRepository.count());
        stats.setActiveCards(cardRepository.countByCardStatus(CardStatus.Active));
        return stats;
    }
}
