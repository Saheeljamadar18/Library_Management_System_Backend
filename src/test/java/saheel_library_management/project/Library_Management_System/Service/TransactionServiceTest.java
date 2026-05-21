package saheel_library_management.project.Library_Management_System.Service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import saheel_library_management.project.Library_Management_System.Entity.Books;
import saheel_library_management.project.Library_Management_System.Entity.Card;
import saheel_library_management.project.Library_Management_System.Enums.CardStatus;
import saheel_library_management.project.Library_Management_System.Repository.BookRepository;
import saheel_library_management.project.Library_Management_System.Repository.CardRepository;
import saheel_library_management.project.Library_Management_System.Repository.TransactionRepository;
import saheel_library_management.project.Library_Management_System.RequestDto.IssueBookRequestDto;
import saheel_library_management.project.Library_Management_System.RequestDto.ReturnBookRequestDto;
import saheel_library_management.project.Library_Management_System.exception.BadRequestException;
import saheel_library_management.project.Library_Management_System.response.TransactionResponseDto;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    TransactionRepository transactionRepository;
    @Mock
    CardRepository cardRepository;
    @Mock
    BookRepository bookRepository;

    @InjectMocks
    TransactionService transactionService;

    @Test
    void issueBook_setsAvailabilityFalseAndCreatesBorrowTransaction() {
        Books book = new Books();
        book.setBook_id(1);
        book.setAvailability(true);

        Card card = new Card();
        card.setId(2);
        card.setCardStatus(CardStatus.Active);
        card.setExpiryDate("2030-12-31");

        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        when(cardRepository.findById(2)).thenReturn(Optional.of(card));
        when(bookRepository.save(any(Books.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(transactionRepository.save(any(saheel_library_management.project.Library_Management_System.Entity.Transaction.class)))
                .thenAnswer(invocation -> {
                    saheel_library_management.project.Library_Management_System.Entity.Transaction t = invocation.getArgument(0);
                    t.setId(10);
                    return t;
                });

        IssueBookRequestDto request = new IssueBookRequestDto();
        request.setBookId(1);
        request.setCardId(2);

        TransactionResponseDto response = transactionService.issueBook(request);

        assertEquals(saheel_library_management.project.Library_Management_System.Enums.Transaction.BORROW, response.getTransactionType());
        assertFalse(book.isAvailability());
        verify(transactionRepository).save(any(saheel_library_management.project.Library_Management_System.Entity.Transaction.class));
    }

    @Test
    void issueBook_throwsWhenBookNotAvailable() {
        Books book = new Books();
        book.setBook_id(1);
        book.setAvailability(false);

        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        IssueBookRequestDto request = new IssueBookRequestDto();
        request.setBookId(1);
        request.setCardId(2);

        assertThrows(BadRequestException.class, () -> transactionService.issueBook(request));
    }

    @Test
    void returnBook_calculatesFineAndSetsAvailabilityTrue() {
        Books book = new Books();
        book.setBook_id(1);
        book.setAvailability(false);

        Card card = new Card();
        card.setId(2);

        saheel_library_management.project.Library_Management_System.Entity.Transaction borrow =
                new saheel_library_management.project.Library_Management_System.Entity.Transaction();
        borrow.setId(5);
        borrow.setTransactionType(saheel_library_management.project.Library_Management_System.Enums.Transaction.BORROW);
        borrow.setBook(book);
        borrow.setCard(card);
        borrow.setDueDate("2020-01-01");

        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        when(cardRepository.findById(2)).thenReturn(Optional.of(card));
        when(transactionRepository.findLatestBorrowByBookId(1)).thenReturn(Optional.of(borrow));
        when(bookRepository.save(any(Books.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(transactionRepository.save(any(saheel_library_management.project.Library_Management_System.Entity.Transaction.class)))
                .thenAnswer(invocation -> {
                    saheel_library_management.project.Library_Management_System.Entity.Transaction t = invocation.getArgument(0);
                    t.setId(11);
                    return t;
                });

        ReturnBookRequestDto request = new ReturnBookRequestDto();
        request.setBookId(1);
        request.setCardId(2);

        TransactionResponseDto response = transactionService.returnBook(request);

        assertEquals(saheel_library_management.project.Library_Management_System.Enums.Transaction.RETURN, response.getTransactionType());
        assertTrue(book.isAvailability());
        assertTrue(response.getOverdueDays() > 0);
        assertTrue(response.getFineAmount() > 0);
    }
}
