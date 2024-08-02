package com.icestormikk.StudentsApplicationServer.services.interfaces;

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
    T updateObjectById(ID id, T object);
    void deleteObjectBy(ID id);
}
