package saheel_library_management.project.Library_Management_System.Converter;

import saheel_library_management.project.Library_Management_System.Entity.Card;
import saheel_library_management.project.Library_Management_System.Entity.Student;
import saheel_library_management.project.Library_Management_System.RequestDto.CardRequestDto;

public class CardConverter {
    // converter acts as the model mapper; used to convert dto to model before saving to the database
    public static Card convertCardRequestDtoToCardModel(CardRequestDto cardRequestDto) {
        Card card = new Card();
        card.setCardStatus(cardRequestDto.getCardStatus());
        card.setExpiryDate(cardRequestDto.getExpiryDate());



        return card;
    }
}
