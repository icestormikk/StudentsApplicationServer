package com.icestormikk.StudentsApplicationServer.controllers;

import com.icestormikk.StudentsApplicationServer.domain.Student;
import com.icestormikk.StudentsApplicationServer.services.interfaces.implementations.StudentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/students")
@Slf4j
public class StudentController {
    @Autowired
    private StudentServiceImpl studentService;

    @GetMapping()
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = this.studentService.getALl();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable(name = "id") Long id) {
        Optional<Student> optionalStudent = this.studentService.getObjectById(id);
        return optionalStudent.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<Student> addStudent(@RequestBody Student studentObj) {
        try {
            Student student = this.studentService.addObject(studentObj);
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable(name = "id") Long id, @RequestBody Student studentObj) {
        Optional<Student> student = this.studentService.updateObjectById(id, studentObj);
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable(name = "id") Long id) {
        try {
            this.studentService.deleteObjectBy(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping()
    public ResponseEntity<Student> deleteStudentByStudentId(@RequestParam(name = "student_id") Long studentId) {
        try {
            this.studentService.deleteObjectByStudentId(studentId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
