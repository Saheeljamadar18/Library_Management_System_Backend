package saheel_library_management.project.Library_Management_System.response;

import saheel_library_management.project.Library_Management_System.Enums.Transaction;

import java.util.Date;

public class TransactionResponseDto {

    private int id;
    private int bookId;
    private int cardId;
    private Transaction transactionType;
    private String dueDate;
    private Date transactionDate;
    private String message;
    private long overdueDays;
    private int fineAmount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Transaction getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Transaction transactionType) {
        this.transactionType = transactionType;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(long overdueDays) {
        this.overdueDays = overdueDays;
    }

    public int getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(int fineAmount) {
        this.fineAmount = fineAmount;
    }
}
