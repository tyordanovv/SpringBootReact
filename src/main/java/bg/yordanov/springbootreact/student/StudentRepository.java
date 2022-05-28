package bg.yordanov.springbootreact.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Repository
public class StudentRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentRepository(JdbcTemplate template){
        this.jdbcTemplate = template;
    }
    public List<Student> selectAllStudents() {
        String sql = "" +
                "SELECT " +
                " student_id, " +
                " first_name, " +
                " last_name, " +
                " email, " +
                " gender " +
                "FROM student";
        return jdbcTemplate.query(sql, getStudentRowMapper());
    }

    public int saveStudent(UUID id, Student student) {
        String sql = "" +
                "INSERT INTO student (" +
                " student_id, " +
                " first_name, " +
                " last_name, " +
                " email, " +
                " gender) " +
                "VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(
                sql,
                id,
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getGender().name().toUpperCase()
        );
    }

    private RowMapper<Student> getStudentRowMapper() {
        return (resultSet, i) -> {
            String idSTR = resultSet.getString("student_id");
            UUID studentId = UUID.fromString(idSTR);

            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String email = resultSet.getString("email");

            String genderSTR = resultSet.getString("gender").toUpperCase();
            Student.Gender gender = Student.Gender.valueOf(genderSTR);
            return new Student(
                    studentId,
                    firstName,
                    lastName,
                    email,
                    gender
            );
        };
    }
}
