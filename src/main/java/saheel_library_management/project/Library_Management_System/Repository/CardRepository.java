package saheel_library_management.project.Library_Management_System.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import saheel_library_management.project.Library_Management_System.Entity.Card;
import saheel_library_management.project.Library_Management_System.Enums.CardStatus;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Integer> {

    @Query(nativeQuery = true, value = "select *from card where student_id =:inputStudentId")
    Card getCardByStudentId(int inputStudentId);

    @Query(nativeQuery = true, value = "select *from card where card_status=:inputStatus")
    List<Card> getCardByStatus(String inputStatus);

    long countByCardStatus(CardStatus cardStatus);
}
