package bg.yordanov.springbootreact.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
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
                "VALUES (?, ?, ?, ?, ?::gender)";
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

    public boolean emailIsTaken(String email) {
        String sql = "" +
                "SELECT EXISTS ( " +
                " SELECT 1 " +
                " FROM student " +
                " WHERE email = ?" +
                ")";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                sql,
                new Object[] {email},
                (resultSet, i) -> resultSet.getBoolean(1)
        ));
    }

    public List<StudentCourse> selectAllStudentCourses(UUID studentId) {
        String sql = "" +
                "SELECT " +
                " student.student_id, " +
                " course.course_id, " +
                " course.course_name, " +
                " course.description, " +
                " course.department, " +
                " course.teacher_name, " +
                " studnet_course.start_date, " +
                " studnet_course.ent_date, " +
                " studnet_course.grade " +
                "FROM student " +
                "JOIN studnet_course    USING (student_id) " +
                "JOIN course            USING (course_id) " +
                "WHERE student.student_id = ?";
        return jdbcTemplate.query(
                sql,
                new Object[]{studentId},
                mapStudentCourseFromDb()
        );
    }

    private RowMapper<StudentCourse> mapStudentCourseFromDb() {
        return (resultSet, i) ->
                new StudentCourse(
                        UUID.fromString(resultSet.getString("student_id")),
                        UUID.fromString(resultSet.getString("course_id")),
                        resultSet.getString("course_name"),
                        resultSet.getString("description"),
                        resultSet.getString("department"),
                        resultSet.getString("teacher_name"),
                        resultSet.getDate("start_date").toLocalDate(),
                        resultSet.getDate("ent_date").toLocalDate(),
                        Optional.ofNullable((resultSet.getString("grade")))
                                .map(Integer::parseInt)
                                .orElse(null)
                );
    }
}
