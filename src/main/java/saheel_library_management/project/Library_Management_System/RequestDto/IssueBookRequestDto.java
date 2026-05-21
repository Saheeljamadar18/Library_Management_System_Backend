package saheel_library_management.project.Library_Management_System.RequestDto;

import jakarta.validation.constraints.Positive;

public class IssueBookRequestDto {

    @Positive(message = "bookId must be greater than 0")
    private int bookId;

    @Positive(message = "cardId must be greater than 0")
    private int cardId;

    private String dueDate;

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

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
