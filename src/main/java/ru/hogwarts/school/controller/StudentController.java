package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("student")
@Tag(name = "API для работы со студентами")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Создание студента")
    public ResponseEntity<Student> create(@RequestBody Student student) {
        Student addedStudent = service.add(student);
        return ResponseEntity.ok(addedStudent);
    }

    @PutMapping
    @Operation(summary = "Обновление студента")
    public ResponseEntity<Student> update(@RequestBody Student student) {
        Student updatedStudent = service.update(student);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление студента")
    public ResponseEntity<Student> remove(@PathVariable Long id) {
        Student deletedStudent = service.remove(id);
        return ResponseEntity.ok(deletedStudent);
    }

    @GetMapping("get-by-id/{id}")
    @Operation(summary = "Получение студента по идентификатору")
    public ResponseEntity<Student> get(@PathVariable Long id) {
        Student student = service.get(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("get-by-age/{age}")
    @Operation(summary = "Получение студентов по возрастному промежутку")
    public ResponseEntity<Collection<Student>> getByAge(@RequestParam Integer minAge, @RequestParam Integer maxAge) {
        Collection<Student> students = service.getByAge(minAge, maxAge);
        return ResponseEntity.ok(students);
    }

    @GetMapping("all")
    @Operation(summary = "Получение всех студентов")
    public ResponseEntity<Collection<Student>> getAll() {
        Collection<Student> students = service.getAll();
        return ResponseEntity.ok(students);
    }

    @GetMapping("faculty/{studentId}")
    @Operation(summary = "Получение факультета по id студента")
    public ResponseEntity<Faculty> getFacultyByStudent(@PathVariable Long studentId) {
        Faculty faculty = service.getFacultyByStudent(studentId);
        return ResponseEntity.ok(faculty);
    }
}
