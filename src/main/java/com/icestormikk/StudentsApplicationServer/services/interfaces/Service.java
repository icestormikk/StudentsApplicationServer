package com.icestormikk.StudentsApplicationServer.services.interfaces;

import com.icestormikk.StudentsApplicationServer.domain.Student;
import com.icestormikk.StudentsApplicationServer.domain.exceptions.StudentAlreadyExistsException;

import java.util.List;
import java.util.Optional;

public interface Service<T, ID> {
    List<T> getALl();
    Optional<T> getObjectById(ID id);
    T addObject(T object);
    Student updateObjectById(ID id, T object);
    void deleteObjectBy(ID id);
}
