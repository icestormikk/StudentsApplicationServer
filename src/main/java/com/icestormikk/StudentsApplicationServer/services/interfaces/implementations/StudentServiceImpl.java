package com.icestormikk.StudentsApplicationServer.services.interfaces.implementations;

import com.icestormikk.StudentsApplicationServer.domain.Student;
import com.icestormikk.StudentsApplicationServer.domain.exceptions.StudentNotFoundException;
import com.icestormikk.StudentsApplicationServer.repositories.StudentRepository;
import com.icestormikk.StudentsApplicationServer.services.interfaces.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository repository;

    @Override
    public List<Student> getALl() {
        log.info("All objects of the Student class are being retrieved");
        return repository.getAll();
    }

    @Override
    public Optional<Student> getObjectById(Long id) {
        log.info("An object of the Student class with id {} is being retrieved", id);
        return repository.getById(id);
    }

    @Override
    public Student addObject(Student object) {
        log.info("The Student object is being added");
        return repository.add(object);
    }

    @Override
    public Optional<Student> updateObjectById(Long id, Student object) {
        try {
            log.info("The Student class object with id {} is being updated", id);
            return Optional.of(repository.updateById(id, object));
        } catch (StudentNotFoundException e) {
            log.error("During the update, the Student class object with id {} could not be found", id);
            return Optional.empty();
        }
    }

    @Override
    public void deleteObjectBy(Long id) {
        try  {
            log.info("An object of the Student class with id {} is being deleted", id);
            repository.deleteById(id);
        } catch (StudentNotFoundException e) {
            log.error("During deletion, the Student class object with id {} could not be found", id);
            throw new RuntimeException(e);
        }
    }
}
