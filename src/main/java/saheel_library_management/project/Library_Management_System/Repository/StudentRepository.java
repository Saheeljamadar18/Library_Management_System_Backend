package saheel_library_management.project.Library_Management_System.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import saheel_library_management.project.Library_Management_System.Entity.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Integer> {
    @Query(nativeQuery = true,value = "select *from student where email =:inputEmail")
    public Student getStudentByEmail(String inputEmail);
   @Query(nativeQuery = true,value = "select *from student where dept=:inputDept")
    public List<Student> getStudentByDept(String inputDept);
}
