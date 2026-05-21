package saheel_library_management.project.Library_Management_System.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import saheel_library_management.project.Library_Management_System.Entity.Books;

import java.util.List;

public interface BookRepository extends JpaRepository<Books, Integer> {

    @Query(nativeQuery = true, value = "select *from books where title =:inputTitle")
    Books getBookByTitle(String inputTitle);

    @Query(nativeQuery = true, value = "select *from books where category=:inputCategory")
    List<Books> getBookByCategory(String inputCategory);

    long countByAvailability(boolean availability);

    List<Books> findByTitleContainingIgnoreCaseOrPublisherNameContainingIgnoreCase(String title, String publisherName);
}
