package com.icestormikk.StudentsApplicationServer.repositories;

import com.icestormikk.StudentsApplicationServer.domain.Student;
import com.icestormikk.StudentsApplicationServer.repositories.interfaces.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StudentRepository implements Repository<Student, Long> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String STUDENTS_TABLE = "students.students";

    @Override
    public List<Student> getAll() {
        return jdbcTemplate.query(
            String.format("SELECT * FROM %s", STUDENTS_TABLE),
            (resultSet, rowNum) -> new Student(
                resultSet.getLong("id"),
                resultSet.getString("firstname"),
                resultSet.getString("lastname"),
                resultSet.getString("patronymic"),
                resultSet.getDate("birthday").toLocalDate(),
                resultSet.getLong("group_id"),
                resultSet.getLong("student_id")
            )
        );
    }

    @Override
    public Optional<Student> getById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Student add() {
        return null;
    }

    @Override
    public Student updateById(Long aLong) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }
}
