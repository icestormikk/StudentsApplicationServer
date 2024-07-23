package com.icestormikk.StudentsApplicationServer.domain;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

/**
 * Класс, описывающий студентов в некотором учебном заведении
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student {
    // уникальный идентификатор студента в БД
    public Long id = 0L;
    // имя студента
    public String firstname;
    // фамилия студента
    public String lastname;
    // отчество студента
    public String patronymic;
    // день рождения студента
    public LocalDate birthday;
    // номер группы, в которой учится студент
    public Long groupId;
    // уникальный номер студента (номер студенческого билета)
    public Long studentId;
}
