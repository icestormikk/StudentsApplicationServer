package com.icestormikk.StudentsApplicationServer.domain.exceptions;

public class StudentNotFoundException extends Throwable {
    public StudentNotFoundException() {
        super("The Student class object could not be found according to the specified criteria");
    }
}
