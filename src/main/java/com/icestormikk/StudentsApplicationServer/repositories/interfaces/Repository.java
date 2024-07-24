package com.icestormikk.StudentsApplicationServer.repositories.interfaces;

import com.icestormikk.StudentsApplicationServer.domain.exceptions.StudentAlreadyExistsException;
import com.icestormikk.StudentsApplicationServer.domain.exceptions.StudentNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс, общий для всех репозиториев приложения
 * @param <T> Тип объекта, к которому будут применяться операции
 * @param <ID> Тип идентификатора объекта
 */
public interface Repository<T, ID> {
    List<T> getAll();
    Optional<T> getById(ID id);
    T add(T object) throws StudentAlreadyExistsException;
    T updateById(ID id, T object) throws StudentNotFoundException;
    void deleteById(ID id) throws StudentNotFoundException;
}
