package com.icestormikk.StudentsApplicationServer.repositories;

import com.icestormikk.StudentsApplicationServer.domain.Student;
import com.icestormikk.StudentsApplicationServer.domain.exceptions.StudentNotFoundException;
import com.icestormikk.StudentsApplicationServer.repositories.interfaces.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Component
public class StudentRepository implements Repository<Student, Long> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String STUDENTS_TABLE = "students.students";

    private Student resultSetToStudent(ResultSet resultSet) throws SQLException {
        return new Student(
            resultSet.getLong("id"),
            resultSet.getString("firstname"),
            resultSet.getString("lastname"),
            resultSet.getString("patronymic"),
            resultSet.getDate("birthday").toLocalDate(),
            resultSet.getLong("group_id"),
            resultSet.getLong("student_id")
        );
    }

    @Override
    public List<Student> getAll() {
        return this.jdbcTemplate.query(
            String.format("SELECT * FROM %s", STUDENTS_TABLE),
            (resultSet, rowIndex) -> resultSetToStudent(resultSet)
        );
    }

    @Override
    public Optional<Student> getById(Long id) {
        try {
            Student student = this.jdbcTemplate.queryForObject(
                    String.format("SELECT * FROM %s WHERE id = ?", STUDENTS_TABLE),
                    (resultSet, rowIndex) -> resultSetToStudent(resultSet),
                    id
            );

            return Optional.ofNullable(student);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Student add(Student object) {
        String sql = String.format(
            "INSERT INTO %s (firstname, lastname, patronymic, birthday, group_id, student_id) VALUES" +
                    "('%s', '%s', '%s', '%s', %d, %d) RETURNING ID",
            STUDENTS_TABLE,
            object.firstname,
            object.lastname,
            object.patronymic,
            object.birthday.format(DateTimeFormatter.ofPattern("uuuu-MM-dd")),
            object.groupId,
            object.studentId
        );

        KeyHolder holder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(
            con -> con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS),
            holder
        );

        return this.getStudentByKey(holder.getKey().longValue());
    }

    @Override
    public Student updateById(Long id, Student object) throws StudentNotFoundException {
        if (this.getById(id).isEmpty()) {
            throw new StudentNotFoundException();
        }

        String sql = String.format(
            "UPDATE %s SET firstname=?, lastname=?, patronymic=?, birthday=?, group_id=?, student_id=? WHERE id=? RETURNING id",
            STUDENTS_TABLE
        );

        KeyHolder holder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(
            (con) -> {
                var ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, object.firstname);
                ps.setString(2, object.lastname);
                ps.setString(3, object.patronymic);
                ps.setDate(4, Date.valueOf(object.birthday));
                ps.setLong(5, object.groupId);
                ps.setLong(6, object.studentId);
                ps.setLong(7, object.id);

                return ps;
            },
            holder
        );

        return this.getStudentByKey(holder.getKey().longValue());
    }

    @Override
    public void deleteById(Long id) throws StudentNotFoundException {
        if (this.getById(id).isEmpty()) {
            throw new StudentNotFoundException();
        }

        this.jdbcTemplate.update(
            String.format("DELETE FROM %s WHERE id = %d", STUDENTS_TABLE, id)
        );
    }

    public void deleteByStudentId(Long studentId) throws StudentNotFoundException {
        Student student = this.jdbcTemplate.queryForObject(
            String.format("SELECT * FROM %s WHERE student_id = %d", STUDENTS_TABLE, studentId),
            (resultSet, rowIndex) -> resultSetToStudent(resultSet)
        );

        if (student == null) {
            throw new StudentNotFoundException();
        }

        this.jdbcTemplate.update(
            String.format("DELETE FROM %s WHERE student_id = %d", STUDENTS_TABLE, studentId)
        );
    }

    private Student getStudentByKey(Number key) {
        Optional<Student> student = this.getById(key.longValue());
        if (student.isEmpty()) {
            throw new IllegalStateException("Student is null");
        }

        return student.get();
    }
}
