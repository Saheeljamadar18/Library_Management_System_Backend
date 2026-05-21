package saheel_library_management.project.Library_Management_System.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import saheel_library_management.project.Library_Management_System.Converter.TransactionConverter;
import saheel_library_management.project.Library_Management_System.Converter.TransactionResponseMapper;
import saheel_library_management.project.Library_Management_System.Entity.Books;
import saheel_library_management.project.Library_Management_System.Entity.Card;
import saheel_library_management.project.Library_Management_System.Entity.Transaction;
import saheel_library_management.project.Library_Management_System.Enums.CardStatus;
import saheel_library_management.project.Library_Management_System.Repository.BookRepository;
import saheel_library_management.project.Library_Management_System.Repository.CardRepository;
import saheel_library_management.project.Library_Management_System.Repository.TransactionRepository;
import saheel_library_management.project.Library_Management_System.RequestDto.IssueBookRequestDto;
import saheel_library_management.project.Library_Management_System.RequestDto.ReturnBookRequestDto;
import saheel_library_management.project.Library_Management_System.RequestDto.TransactionRequestDto;
import saheel_library_management.project.Library_Management_System.exception.BadRequestException;
import saheel_library_management.project.Library_Management_System.exception.ResourceNotFoundException;
import saheel_library_management.project.Library_Management_System.response.TransactionResponseDto;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TransactionService {

    private static final int DEFAULT_BORROW_DAYS = 14;
    private static final int FINE_PER_OVERDUE_DAY = 5;

    private static final Set<String> ALLOWED_SORT_FIELDS =
            Set.of("id", "transactiondate", "dueDate", "transactionType");

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    BookRepository bookRepository;

    @Transactional
    public TransactionResponseDto issueBook(IssueBookRequestDto request) {
        Books book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + request.getBookId()));

        if (!book.isAvailability()) {
            throw new BadRequestException("Book is not available for issue.");
        }

        Card card = cardRepository.findById(request.getCardId())
                .orElseThrow(() -> new ResourceNotFoundException("Card not found with id: " + request.getCardId()));

        validateActiveCard(card);

        Transaction transaction = new Transaction();
        transaction.setTransactionType(saheel_library_management.project.Library_Management_System.Enums.Transaction.BORROW);
        transaction.setBook(book);
        transaction.setCard(card);
        transaction.setDueDate(resolveDueDate(request.getDueDate()));

        book.setAvailability(false);
        bookRepository.save(book);
        transactionRepository.save(transaction);

        return TransactionResponseMapper.toDto(transaction, "Book issued successfully", 0, 0);
    }

    @Transactional
    public TransactionResponseDto returnBook(ReturnBookRequestDto request) {
        Books book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + request.getBookId()));

        if (book.isAvailability()) {
            throw new BadRequestException("Book is not currently borrowed.");
        }

        Card card = cardRepository.findById(request.getCardId())
                .orElseThrow(() -> new ResourceNotFoundException("Card not found with id: " + request.getCardId()));

        Transaction borrowTransaction = transactionRepository.findLatestBorrowByBookId(book.getBook_id())
                .orElseThrow(() -> new BadRequestException("No borrow record found for this book."));

        if (borrowTransaction.getCard().getId() != card.getId()) {
            throw new BadRequestException("This card did not borrow this book.");
        }

        long overdueDays = calculateOverdueDays(borrowTransaction.getDueDate());
        int fineAmount = (int) (overdueDays * FINE_PER_OVERDUE_DAY);

        Transaction returnTransaction = new Transaction();
        returnTransaction.setTransactionType(saheel_library_management.project.Library_Management_System.Enums.Transaction.RETURN);
        returnTransaction.setBook(book);
        returnTransaction.setCard(card);
        returnTransaction.setDueDate(borrowTransaction.getDueDate());

        book.setAvailability(true);
        bookRepository.save(book);
        transactionRepository.save(returnTransaction);

        return TransactionResponseMapper.toDto(
                returnTransaction,
                fineAmount > 0 ? "Book returned. Fine due: " + fineAmount : "Book returned successfully",
                overdueDays,
                fineAmount);
    }

    public String saveTransaction(TransactionRequestDto transactionRequestDto) {
        Transaction transaction = TransactionConverter.convertTransactionRequestDtoToTransactionModel(transactionRequestDto);

        if (transactionRequestDto.getBookId() <= 0) {
            throw new BadRequestException("Book id is required.");
        }
        Books books = bookRepository.findById(transactionRequestDto.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + transactionRequestDto.getBookId()));
        transaction.setBook(books);

        if (transactionRequestDto.getCardId() <= 0) {
            throw new BadRequestException("Card id is required.");
        }
        Card card = cardRepository.findById(transactionRequestDto.getCardId())
                .orElseThrow(() -> new ResourceNotFoundException("Card not found with id: " + transactionRequestDto.getCardId()));
        transaction.setCard(card);

        transactionRepository.save(transaction);
        return "transaction saved successfully !";
    }

    public Transaction getTransactionById(int id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("transaction not found with id " + id));
    }

    public List<Transaction> getAllTransaction() {
        return transactionRepository.findAll();
    }

    public String deleteById(int id) {
        if (!transactionRepository.existsById(id)) {
            throw new ResourceNotFoundException("transaction not found with id " + id);
        }
        transactionRepository.deleteById(id);
        return "transaction with id:" + id + " is got deleted";
    }

    public String updateById(int id, TransactionRequestDto newTransactionRequestDto) {
        Transaction existingTransaction = getTransactionById(id);
        existingTransaction.setDueDate(newTransactionRequestDto.getDueDate());
        existingTransaction.setTransactionType(newTransactionRequestDto.getTransactionType());

        if (newTransactionRequestDto.getBookId() > 0) {
            Books books = bookRepository.findById(newTransactionRequestDto.getBookId())
                    .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + newTransactionRequestDto.getBookId()));
            existingTransaction.setBook(books);
        }

        if (newTransactionRequestDto.getCardId() > 0) {
            Card card = cardRepository.findById(newTransactionRequestDto.getCardId())
                    .orElseThrow(() -> new ResourceNotFoundException("Card not found with id: " + newTransactionRequestDto.getCardId()));
            existingTransaction.setCard(card);
        }

        transactionRepository.save(existingTransaction);
        return "transaction updated successfully!";
    }

    public List<Transaction> getTransactionByPagination(int pageNo, int pageSize, String sortBy, String sortDir) {
        String field = ALLOWED_SORT_FIELDS.contains(sortBy) ? sortBy : "id";
        Sort sort = "desc".equalsIgnoreCase(sortDir)
                ? Sort.by(field).descending()
                : Sort.by(field).ascending();
        return transactionRepository.findAll(PageRequest.of(pageNo, pageSize, sort)).getContent();
    }

    public List<Transaction> getByBookId(int bookId) {
        return transactionRepository.getTransactionByBookId(bookId);
    }

    public List<Transaction> getByCardId(int cardId) {
        return transactionRepository.getTransactionByCardId(cardId);
    }

    private void validateActiveCard(Card card) {
        if (card.getCardStatus() != CardStatus.Active) {
            throw new BadRequestException("Card is not active. Status: " + card.getCardStatus());
        }
        try {
            LocalDate expiry = LocalDate.parse(card.getExpiryDate());
            if (expiry.isBefore(LocalDate.now())) {
                throw new BadRequestException("Card has expired on " + card.getExpiryDate());
            }
        } catch (DateTimeParseException ex) {
            throw new BadRequestException("Invalid card expiry date format. Use yyyy-MM-dd");
        }
    }

    private String resolveDueDate(String dueDate) {
        if (dueDate != null && !dueDate.isBlank()) {
            try {
                LocalDate.parse(dueDate);
                return dueDate;
            } catch (DateTimeParseException ex) {
                throw new BadRequestException("Invalid dueDate format. Use yyyy-MM-dd");
            }
        }
        return LocalDate.now().plusDays(DEFAULT_BORROW_DAYS).toString();
    }

    private long calculateOverdueDays(String dueDate) {
        if (dueDate == null || dueDate.isBlank()) {
            return 0;
        }
        try {
            LocalDate due = LocalDate.parse(dueDate);
            long days = ChronoUnit.DAYS.between(due, LocalDate.now());
            return Math.max(days, 0);
        } catch (DateTimeParseException ex) {
            return 0;
        }
    }
}
