package com.icestormikk.StudentsApplicationServer.controllers;

import com.icestormikk.StudentsApplicationServer.domain.Student;
import com.icestormikk.StudentsApplicationServer.services.interfaces.implementations.StudentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Класс для обработки поступающих от пользователей запросов
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/students")
@Slf4j
public class StudentController {
    @Autowired
    private StudentServiceImpl studentService;

    /**
     * Обработчик запроса на получение списка всех студентов
     * @return Список, содержащий всех имеющихся в БД студентов
     */
    @GetMapping()
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = this.studentService.getAll();
        return ResponseEntity.ok(students);
    }

    /**
     * Обработчик запроса на получения студента по его ID
     * @param id идентификатор студента, которого нужно найти
     * @return Объект класса Student с нужным ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable(name = "id") Long id) {
        Optional<Student> optionalStudent = this.studentService.getObjectById(id);
        return optionalStudent.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Обработчик запроса на добавление студента
     * @param studentObj объект с информацией о новом студенте
     * @return Объект, который был создан на основе информации из studentObj
     */
    @PostMapping()
    public ResponseEntity<Student> addStudent(@RequestBody Student studentObj) {
        Student student = this.studentService.addObject(studentObj);
        return ResponseEntity.ok(student);
    }

    /**
     * Обработчик запроса на обновление студента по его id
     * @param id идентификатор студента, которого нужно обновить
     * @param studentObj объект с новой информацией о студенте
     * @return Обновленный объект класса Student
     */
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable(name = "id") Long id, @RequestBody Student studentObj) {
        Student student = this.studentService.updateObjectById(id, studentObj);
        return ResponseEntity.ok(student);
    }

    /**
     * Обработчик запроса на удаление студента по его id
     * @param id идентификатор студента, которого нужно удалить
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable(name = "id") Long id) {
        this.studentService.deleteObjectBy(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Обработчик запроса на удаление студента по его student_id
     * @param studentId уникальный номер студента, которого нужно удалить
     */
    @DeleteMapping()
    public ResponseEntity<Student> deleteStudentByStudentId(@RequestParam(name = "student_id") Long studentId) {
        this.studentService.deleteObjectByStudentId(studentId);
        return ResponseEntity.ok().build();
    }
}
