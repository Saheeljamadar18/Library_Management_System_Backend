package saheel_library_management.project.Library_Management_System.Converter;

import saheel_library_management.project.Library_Management_System.Entity.Transaction;
import saheel_library_management.project.Library_Management_System.response.TransactionResponseDto;

public class TransactionResponseMapper {

    private TransactionResponseMapper() {
    }

    public static TransactionResponseDto toDto(Transaction transaction, String message, long overdueDays, int fineAmount) {
        TransactionResponseDto dto = new TransactionResponseDto();
        dto.setId(transaction.getId());
        dto.setBookId(transaction.getBook().getBook_id());
        dto.setCardId(transaction.getCard().getId());
        dto.setTransactionType(transaction.getTransactionType());
        dto.setDueDate(transaction.getDueDate());
        dto.setTransactionDate(transaction.getTransactiondate());
        dto.setMessage(message);
        dto.setOverdueDays(overdueDays);
        dto.setFineAmount(fineAmount);
        return dto;
    }
}
