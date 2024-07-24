package com.icestormikk.StudentsApplicationServer.domain.exceptions;

/**
 * Исключение для обозначения ситуации, когда не удалось найти студента
 */
public class StudentNotFoundException extends Throwable {
    public StudentNotFoundException() {
        super("The Student class object could not be found according to the specified criteria");
    }
}
