package saheel_library_management.project.Library_Management_System.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import saheel_library_management.project.Library_Management_System.Converter.CardConverter;
import saheel_library_management.project.Library_Management_System.Entity.Card;
import saheel_library_management.project.Library_Management_System.Entity.Student;
import saheel_library_management.project.Library_Management_System.Repository.CardRepository;
import saheel_library_management.project.Library_Management_System.Repository.StudentRepository;
import saheel_library_management.project.Library_Management_System.RequestDto.CardRequestDto;
import saheel_library_management.project.Library_Management_System.exception.BadRequestException;
import saheel_library_management.project.Library_Management_System.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Set;

@Service
public class CardService {

    private static final Set<String> ALLOWED_SORT_FIELDS =
            Set.of("id", "cardStatus", "expiryDate", "createdDate", "updatedDate");

    @Autowired
    CardRepository cardRepository;
    @Autowired
    StudentRepository studentRepository;

    public String saveCard(CardRequestDto cardRequestDto){
        Card card = CardConverter.convertCardRequestDtoToCardModel(cardRequestDto);

        if (cardRequestDto.getStudentId() <= 0) {
            throw new BadRequestException("Student id is required. Save a student first, then use its id in studentId.");
        }

        Student student = studentRepository.findById(cardRequestDto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + cardRequestDto.getStudentId()));
        card.setStudent(student);

        cardRepository.save(card);
        return "card saved successfully !";
    }

    public Card getCardById(int id){
        return cardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("card not found with id " + id));
    }

    public List<Card> getAllCard() {
        return cardRepository.findAll();
    }

    public String deleteById(int id){
        cardRepository.deleteById(id);
        return "card with id:"+id+"is got deleted";
    }

    public String updateById(int id, CardRequestDto newCardRequestDto){
        Card existingCard = getCardById(id);
        existingCard.setCardStatus(newCardRequestDto.getCardStatus());
        existingCard.setExpiryDate(newCardRequestDto.getExpiryDate());

        if (newCardRequestDto.getStudentId() > 0) {
            Student student = studentRepository.findById(newCardRequestDto.getStudentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + newCardRequestDto.getStudentId()));
            existingCard.setStudent(student);
        }

        cardRepository.save(existingCard);
        return "card updated successfully!";
    }

    public List<Card> getCardByPagination(int pageNo, int pageSize, String sortBy, String sortDir) {
        String field = ALLOWED_SORT_FIELDS.contains(sortBy) ? sortBy : "id";
        Sort sort = "desc".equalsIgnoreCase(sortDir)
                ? Sort.by(field).descending()
                : Sort.by(field).ascending();
        return cardRepository.findAll(PageRequest.of(pageNo, pageSize, sort)).getContent();
    }

    public Card getByStudentId(int studentId){
        return cardRepository.getCardByStudentId(studentId);
    }

    public List<Card> getByStatus(String inputStatus){
        return cardRepository.getCardByStatus(inputStatus);
    }
}
