package com.icestormikk.StudentsApplicationServer.domain.exceptions;

/**
 * Исключение для обозначения ситуаций добавления дубликата какого-либо объекта
 */
public class StudentAlreadyExistsException extends Throwable {
    public StudentAlreadyExistsException() {
        super("An object of the Student class with such data already exists");
    }
}
