package saheel_library_management.project.Library_Management_System.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import saheel_library_management.project.Library_Management_System.Converter.StudentConverter;
import saheel_library_management.project.Library_Management_System.Entity.Card;
import saheel_library_management.project.Library_Management_System.Entity.Student;
import saheel_library_management.project.Library_Management_System.Enums.CardStatus;
import saheel_library_management.project.Library_Management_System.Repository.StudentRepository;
import saheel_library_management.project.Library_Management_System.RequestDto.StudentRequestDto;
import saheel_library_management.project.Library_Management_System.exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class StudentService {

    private static final Set<String> ALLOWED_SORT_FIELDS =
            Set.of("id", "name", "email", "dept", "sem", "gender", "address", "dob", "mobile");

    @Autowired
    StudentRepository studentRepository;
    public String saveStudent(StudentRequestDto studentRequestDto){
        Student student= StudentConverter.convertStudentRequestDtoToStudentModel(studentRequestDto);
        Card card=new Card();
        card.setCardStatus(CardStatus.Active);
        LocalDate expiry = LocalDate.now().plusYears(4);
        card.setExpiryDate(expiry.toString());
        card.setStudent(student);
        student.setCard(card);
        studentRepository.save(student);
        return "student saved successfully !";

    }
        public Student getStudentById(int id){
            return studentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("student not found with id " + id));
   }
   public List<Student> getAllStudent() {
       List<Student> studentList = studentRepository.findAll();
       return studentList;
   }
   public String  deleteById(int id){
        studentRepository.deleteById(id);
        return "student with id:"+id+"is got deleted";
   }
   public String updateById(int id,StudentRequestDto newstudentRequestDto){
        Student existingStudent = getStudentById(id);
        existingStudent.setName(newstudentRequestDto.getName());
        existingStudent.setMobile(newstudentRequestDto.getMobile());
        existingStudent.setSem(newstudentRequestDto.getSem());
        existingStudent.setDob(newstudentRequestDto.getDob());
        existingStudent.setDept(newstudentRequestDto.getDept());
        existingStudent.setGender(newstudentRequestDto.getGender());
        existingStudent.setEmail(newstudentRequestDto.getEmail());
        existingStudent.setAddress(newstudentRequestDto.getAddress());
        studentRepository.save(existingStudent);
        return "student updated successfully!";
   }
   public List<Student> getStudentByPagination(int pageNo, int pageSize, String sortBy, String sortDir) {
        String field = ALLOWED_SORT_FIELDS.contains(sortBy) ? sortBy : "id";
        Sort sort = "desc".equalsIgnoreCase(sortDir)
                ? Sort.by(field).descending()
                : Sort.by(field).ascending();
        return studentRepository.findAll(PageRequest.of(pageNo, pageSize, sort)).getContent();
   }
  public Student getByEmail(String email){
        Student student=studentRepository.getStudentByEmail(email);
        return student;
  }
  public List<Student> getByDept(String inputdept){
         List<Student>student=studentRepository.getStudentByDept(inputdept);
         return student;
  }
}
