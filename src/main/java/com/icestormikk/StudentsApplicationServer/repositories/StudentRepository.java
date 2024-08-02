package com.icestormikk.StudentsApplicationServer.repositories;

import com.icestormikk.StudentsApplicationServer.domain.Student;
import com.icestormikk.StudentsApplicationServer.domain.exceptions.StudentAlreadyExistsException;
import com.icestormikk.StudentsApplicationServer.domain.exceptions.StudentNotFoundException;
import com.icestormikk.StudentsApplicationServer.repositories.interfaces.Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для взаимодействия с объектами класса Student
 */
@Component
@Slf4j
public class StudentRepository implements Repository<Student, Long> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String STUDENTS_TABLE = "students.students";

    /**
     * Функция преобразования набора данных из БД в объект класса Student
     * @param resultSet Набор данных из БД
     * @return Объект класса Student
     */
    private Student resultSetToStudent(ResultSet resultSet) throws SQLException {
        return new Student(
            resultSet.getString("firstname"),
            resultSet.getString("lastname"),
            resultSet.getString("patronymic"),
            resultSet.getDate("birthday").toLocalDate(),
            resultSet.getLong("group_id"),
            resultSet.getLong("student_id"),
            resultSet.getLong("id")
        );
    }

    /**
     * Получение всех имеющихся в БД объектов Student
     * @return Список со всеми объектами класса Student
     */
    @Override
    public List<Student> getAll() {
        return this.jdbcTemplate.query(
            String.format("SELECT * FROM %s", STUDENTS_TABLE),
            (resultSet, rowIndex) -> resultSetToStudent(resultSet)
        );
    }

    /**
     * Получение студента по его id
     * @param id идентификатор студента, которого надо найти
     * @return Объект класса Student, если поиск завершился удачно, Optional.empty иначе
     */
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

    /**
     * Добавление студента в базу данных
     * @param object Объект с информацией о новом студенте
     * @return Новосозданный объект класса Student
     * @throws StudentAlreadyExistsException
     */
    @Override
    public Student add(Student object) throws StudentAlreadyExistsException {
        if (this.getStudentByStudentId(object.studentId).isPresent()) {
            throw new StudentAlreadyExistsException();
        }

        String sql = String.format(
            "INSERT INTO %s (firstname, lastname, patronymic, birthday, group_id, student_id) VALUES" +
                    "(?, ?, ?, ?, ?, ?) RETURNING ID",
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
                return ps;
            },
            holder
        );

        return this.getStudentByKey(holder.getKey().longValue());
    }

    /**
     * Обновление объекта Student о его id
     * @param id идентификатор студента, которого необходимо обновить
     * @param object Объект с новой информацией о студенте
     * @return Обновлённый объект класса Student
     * @throws StudentNotFoundException
     */
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

    /**
     * Удаление студента по его id
     * @param id идентификатор студента, которого необходимо удалить
     * @throws StudentNotFoundException
     */
    @Override
    public void deleteById(Long id) throws StudentNotFoundException {
        if (this.getById(id).isEmpty()) {
            throw new StudentNotFoundException();
        }

        this.jdbcTemplate.update(
            String.format("DELETE FROM %s WHERE id = %d", STUDENTS_TABLE, id)
        );
    }

    /**
     * Удаление студента по его student_id
     * @param studentId уникальный номер студента, которого необходимо удалить
     * @throws StudentNotFoundException
     */
    public void deleteByStudentId(Long studentId) throws StudentNotFoundException {
        if (this.getStudentByStudentId(studentId).isEmpty()) {
            throw new StudentNotFoundException();
        }

        String sql = String.format("DELETE FROM %s WHERE student_id = ?", STUDENTS_TABLE);

        this.jdbcTemplate.update(
            (con) -> {
                var ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, studentId);
                return ps;
            }
        );
    }

    /**
     * Получение объекта по ключу, возвращенному из БД
     * @param key Ключ, который ссылается на нужный объект
     * @return Объект класса Student
     */
    private Student getStudentByKey(Number key) {
        Optional<Student> student = this.getById(key.longValue());
        if (student.isEmpty()) {
            throw new IllegalStateException("Student is null");
        }

        return student.get();
    }

    /**
     * Получение студента по его student_id
     * @param studentId уникальный номер студента, которого надо найти
     * @return Объект класса Student, если поиск завершился удачно, Optional.empty иначе
     */
    private Optional<Student> getStudentByStudentId(Long studentId) {
        try {
            Student student = this.jdbcTemplate.queryForObject(
                String.format("SELECT * FROM %s WHERE student_id = %d", STUDENTS_TABLE, studentId),
                (resultSet, rowIndex) -> resultSetToStudent(resultSet)
            );

            return Optional.ofNullable(student);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
