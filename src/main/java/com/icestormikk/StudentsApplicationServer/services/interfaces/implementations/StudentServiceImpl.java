package com.icestormikk.StudentsApplicationServer.services.interfaces.implementations;

import com.icestormikk.StudentsApplicationServer.domain.Student;
import com.icestormikk.StudentsApplicationServer.repositories.StudentRepository;
import com.icestormikk.StudentsApplicationServer.services.interfaces.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository repository;

    @Override
    public List<Student> getALl() {
        return repository.getAll();
    }

    @Override
    public Student getObjectById(Long aLong) {
        return null;
    }

    @Override
    public Student addObject(Student object) {
        return null;
    }

    @Override
    public Student updateObjectById(Long aLong, Student object) {
        return null;
    }

    @Override
    public void deleteObjectBy(Long aLong) {

    }
}
