package saheel_library_management.project.Library_Management_System.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import saheel_library_management.project.Library_Management_System.Entity.Student;
import saheel_library_management.project.Library_Management_System.RequestDto.StudentRequestDto;
import saheel_library_management.project.Library_Management_System.Service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student/apis")
public class StudentController {
    @Autowired
    StudentService studentService;

    @PostMapping("/save")
    public String saveStudent(@Valid @RequestBody StudentRequestDto studentRequestDto){
        return studentService.saveStudent(studentRequestDto);
    }

    @GetMapping("/find/{id}")
    public Student findStudentById(@PathVariable int id){
        return studentService.getStudentById(id);
    }

    @GetMapping("/findAll")
    public List<Student> findAll(){
        return studentService.getAllStudent();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteStudentById(@PathVariable int id){
        return studentService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public String updateStudent(@PathVariable int id, @Valid @RequestBody StudentRequestDto studentRequestDto){
        return studentService.updateById(id, studentRequestDto);
    }

    @GetMapping("/findAllByPage")
    public List<Student> findAllByPage(
            @RequestParam(name = "pageNo", required = false) Integer pageNo,
            @RequestParam(name = "PageNo", required = false) Integer pageNoLegacy,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir) {
        int resolvedPageNo = pageNo != null ? pageNo : (pageNoLegacy != null ? pageNoLegacy : 0);
        return studentService.getStudentByPagination(resolvedPageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/findByEmail")
    public Student getByEmail(@RequestParam String email){
        return studentService.getByEmail(email);
    }

    @GetMapping("/findByDept")
    public List<Student> getByDept(@RequestParam String Dept){
        return studentService.getByDept(Dept);
    }
}
