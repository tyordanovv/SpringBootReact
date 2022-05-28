package bg.yordanov.springbootreact.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
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
        //TODO verify that email is not take

        studentRepository.saveStudent(id, student);
    }
}
