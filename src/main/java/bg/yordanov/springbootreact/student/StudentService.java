package bg.yordanov.springbootreact.student;

import bg.yordanov.springbootreact.exeption.ApiRequestException;
import bg.yordanov.springbootreact.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final EmailValidator emailValidator;

    @Autowired
    public StudentService(
            StudentRepository studentRepository,
            EmailValidator emailValidator){
        this.studentRepository = studentRepository;
        this.emailValidator = emailValidator;
    }

    public List<Student> getAllStudents(){
        return studentRepository.selectAllStudents();
    }

    public void addNewStudent(Student student) {
        addNewStudent(null, student);
    }
    public void addNewStudent(UUID studentId, Student student) {
        UUID id = Optional.ofNullable(studentId)
                .orElse(UUID.randomUUID());

        if (!emailValidator.test(student.getEmail())){
            throw new ApiRequestException(student.getEmail() + " is not a valid email address!");
        }else if (studentRepository.emailIsTaken(student.getEmail())){
            throw new ApiRequestException(student.getEmail() + " is already taken!");
        }
        //TODO verify that email is not take and is appropriate

        studentRepository.saveStudent(id, student);
    }

    public List<StudentCourse> getAllCoursesForStudent(UUID studentId) {
        return studentRepository.selectAllStudentCourses(studentId);
    }
}
