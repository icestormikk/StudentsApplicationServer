package com.icestormikk.StudentsApplicationServer.repositories.interfaces;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {
    List<T> getAll();
    Optional<T> getById(ID id);
    T add();
    T updateById(ID id);
    void deleteById(ID id);
}
