package com.icestormikk.StudentsApplicationServer.repositories.interfaces;

import com.icestormikk.StudentsApplicationServer.domain.exceptions.StudentNotFoundException;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {
    List<T> getAll();
    Optional<T> getById(ID id);
    T add(T object);
    T updateById(ID id, T object) throws StudentNotFoundException;
    void deleteById(ID id) throws StudentNotFoundException;
}
