package com.icestormikk.StudentsApplicationServer.services.interfaces.implementations;

import com.icestormikk.StudentsApplicationServer.domain.Student;
import com.icestormikk.StudentsApplicationServer.domain.exceptions.StudentAlreadyExistsException;
import com.icestormikk.StudentsApplicationServer.domain.exceptions.StudentNotFoundException;
import com.icestormikk.StudentsApplicationServer.repositories.StudentRepository;
import com.icestormikk.StudentsApplicationServer.services.interfaces.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для взаимодействия с объектами класса Student
 */
@Slf4j
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository repository;

    /**
     * Получение всех имеющихся в БД объектов Student
     * @return Список со всеми объектами класса Student
     */
    @Override
    public List<Student> getAll() {
        log.info("All objects of the Student class are being retrieved");
        return this.repository.getAll();
    }

    /**
     * Получение студента по его id
     * @param id идентификатор студента, которого надо найти
     * @return Объект класса Student, если поиск завершился удачно, Optional.empty иначе
     */
    @Override
    public Optional<Student> getObjectById(Long id) {
        log.info("An object of the Student class with id {} is being retrieved", id);
        return this.repository.getById(id);
    }

    /**
     * Добавление студента в базу данных
     * @param object Объект с информацией о новом студенте
     * @return Новосозданный объект класса Student
     */
    @Override
    public Student addObject(Student object) {
        try {
            log.info("The Student object is being added");
            return this.repository.add(object);
        } catch (StudentAlreadyExistsException e) {
            log.error("An error occurred while adding an object {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Обновление объекта Student о его id
     *
     * @param id     идентификатор студента, которого необходимо обновить
     * @param object Объект с новой информацией о студенте
     * @return Обновлённый объект класса Student
     */
    @Override
    public Student updateObjectById(Long id, Student object) {
        try {
            log.info("The Student class object with id {} is being updated", id);
            return this.repository.updateById(id, object);
        } catch (StudentNotFoundException e) {
            log.error("During the update, the Student class object with id {} could not be found", id);
            throw new RuntimeException(e);
        }
    }

    /**
     * Удаление студента по его id
     * @param id идентификатор студента, которого необходимо удалить
     */
    @Override
    public void deleteObjectBy(Long id) {
        try  {
            log.info("An object of the Student class with id {} is being deleted", id);
            this.repository.deleteById(id);
        } catch (StudentNotFoundException e) {
            log.error("During deletion, the Student class object with id {} could not be found", id);
            throw new RuntimeException(e);
        }
    }

    /**
     * Удаление студента по его student_id
     * @param id уникальный номер студента, которого необходимо удалить
     */
    public void deleteObjectByStudentId(Long id) {
        try {
            log.info("An object of the Student class with student_id {} is being deleted", id);
            this.repository.deleteByStudentId(id);
        } catch (StudentNotFoundException e) {
            log.error("During deletion, the Student class object with student_id {} could not be found", id);
            throw new RuntimeException(e);
        }
    }
}
