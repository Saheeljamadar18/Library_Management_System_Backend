package saheel_library_management.project.Library_Management_System.Converter;

import saheel_library_management.project.Library_Management_System.Entity.Books;
import saheel_library_management.project.Library_Management_System.Entity.Card;
import saheel_library_management.project.Library_Management_System.Entity.Transaction;
import saheel_library_management.project.Library_Management_System.RequestDto.TransactionRequestDto;

public class TransactionConverter {
    // converter acts as the model mapper; used to convert dto to model before saving to the database
    public static Transaction convertTransactionRequestDtoToTransactionModel(TransactionRequestDto transactionRequestDto) {
        Transaction transaction = new Transaction();
        transaction.setDueDate(transactionRequestDto.getDueDate());
        transaction.setTransactionType(transactionRequestDto.getTransactionType());



        return transaction;
    }
}
