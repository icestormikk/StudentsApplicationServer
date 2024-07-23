package com.icestormikk.StudentsApplicationServer.services.interfaces;

import java.util.List;

public interface Service<T, ID> {
    List<T> getALl();
    T getObjectById(ID id);
    T addObject(T object);
    T updateObjectById(ID id,T object);
    void deleteObjectBy(ID id);
}
