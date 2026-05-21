package saheel_library_management.project.Library_Management_System.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import saheel_library_management.project.Library_Management_System.Entity.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author,Integer> {
    @Query(nativeQuery = true,value = "select *from author where email =:inputEmail")
    public Author getAuthorByEmail(String inputEmail);
    @Query(nativeQuery = true,value = "select *from author where country=:inputCountry")
    public List<Author> getAuthorByCountry(String inputCountry);
}
