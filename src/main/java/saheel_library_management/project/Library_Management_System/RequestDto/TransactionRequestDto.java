package saheel_library_management.project.Library_Management_System.RequestDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import saheel_library_management.project.Library_Management_System.Enums.Transaction;

public class TransactionRequestDto {
    private String dueDate;

    @NotNull(message = "transactionType is required")
    private Transaction transactionType;

    @Positive(message = "bookId must be greater than 0")
    private int bookId;

    @Positive(message = "cardId must be greater than 0")
    private int cardId;

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Transaction getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Transaction transactionType) {
        this.transactionType = transactionType;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }
}
