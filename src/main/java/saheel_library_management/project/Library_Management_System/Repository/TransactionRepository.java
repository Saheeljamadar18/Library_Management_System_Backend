package saheel_library_management.project.Library_Management_System.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import saheel_library_management.project.Library_Management_System.Entity.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query(nativeQuery = true, value = "select *from transaction where book_id =:inputBookId")
    List<Transaction> getTransactionByBookId(int inputBookId);

    @Query(nativeQuery = true, value = "select *from transaction where card_id=:inputCardId")
    List<Transaction> getTransactionByCardId(int inputCardId);

    @Query("SELECT t FROM Transaction t WHERE t.book.book_id = :bookId AND t.transactionType = :type ORDER BY t.id DESC")
    List<Transaction> findByBookIdAndType(
            @Param("bookId") int bookId,
            @Param("type") saheel_library_management.project.Library_Management_System.Enums.Transaction type);

    default Optional<Transaction> findLatestBorrowByBookId(int bookId) {
        List<Transaction> list = findByBookIdAndType(bookId, saheel_library_management.project.Library_Management_System.Enums.Transaction.BORROW);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.getFirst());
    }
}
