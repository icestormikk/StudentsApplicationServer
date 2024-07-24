package com.icestormikk.StudentsApplicationServer.services.interfaces;

import com.icestormikk.StudentsApplicationServer.domain.Student;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс, общий для всех сервисов приложения
 * @param <T> Тип объекта, к которому будут применяться операции
 * @param <ID> Тип идентификатора объекта
 */
public interface Service<T, ID> {
    List<T> getAll();
    Optional<T> getObjectById(ID id);
    T addObject(T object);
    Student updateObjectById(ID id, T object);
    void deleteObjectBy(ID id);
}
