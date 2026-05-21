package saheel_library_management.project.Library_Management_System.RequestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import saheel_library_management.project.Library_Management_System.Enums.CardStatus;

public class CardRequestDto {

    @NotNull(message = "cardStatus is required")
    private CardStatus CardStatus;

    @NotBlank(message = "expiryDate is required")
    private String expiryDate;

    @Positive(message = "studentId must be greater than 0")
    private int studentId;


    public CardStatus getCardStatus() {
        return CardStatus;
    }

    public void setCardStatus(CardStatus cardStatus) {
        CardStatus = cardStatus;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
