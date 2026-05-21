package saheel_library_management.project.Library_Management_System.Converter;

import saheel_library_management.project.Library_Management_System.Entity.Student;
import saheel_library_management.project.Library_Management_System.RequestDto.StudentRequestDto;

public class StudentConverter {
    //converter act as the modalMapper it is used to convert to the dto to model the-we can save to the database
    public static Student convertStudentRequestDtoToStudentModel(StudentRequestDto studentRequestDto) {
        Student student = new Student();
        student.setName(studentRequestDto.getName());
        student.setEmail(studentRequestDto.getEmail());
        student.setAddress(studentRequestDto.getAddress());
        student.setGender(studentRequestDto.getGender());
        student.setDept(studentRequestDto.getDept());
        student.setDob(studentRequestDto.getDob());
        student.setSem(studentRequestDto.getSem());
        student.setMobile(studentRequestDto.getMobile());

        return student;
    }
}
