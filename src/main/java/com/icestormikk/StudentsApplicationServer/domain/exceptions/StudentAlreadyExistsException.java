package com.icestormikk.StudentsApplicationServer.domain.exceptions;

public class StudentAlreadyExistsException extends Throwable {
    public StudentAlreadyExistsException() {
        super("An object of the Student class with such data already exists");
    }
}
