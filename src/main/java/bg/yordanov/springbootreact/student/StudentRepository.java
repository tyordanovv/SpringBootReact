package bg.yordanov.springbootreact.student;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class StudentRepository {
    public List<Student> selectAllStudents() {
        return List.of(
                new Student(
                        UUID.randomUUID(),
                        "Tihomir",
                        "Yordanov",
                        "tisho@gmal.com",
                        Student.Gender.MALE
                ),
                new Student(
                        UUID.randomUUID(),
                        "Sami",
                        "Samuilov",
                        "sami@gmal.com",
                        Student.Gender.MALE
                )
        );
    }
}
