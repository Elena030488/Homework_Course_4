package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.stream.Stream;

@RestController
@RequestMapping("faculty")
@Tag(name = "API для работы с факультетами")
public class FacultyController {
    private final FacultyService service;

    public FacultyController(FacultyService service) {
        this.service = service;
    }
    @PostMapping
    @Operation(summary = "Создание факультета")
    public ResponseEntity<Faculty> create(@RequestBody Faculty faculty) {
        Faculty addedFaculty = service.add(faculty);
        return ResponseEntity.ok(addedFaculty);
    }

    @PutMapping
    @Operation(summary = "Обновление факультета")
    public ResponseEntity<Faculty> update(@RequestBody Faculty faculty) {
        Faculty updatedFaculty = service.update(faculty);
        return ResponseEntity.ok(updatedFaculty);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление факультета")
    public ResponseEntity<Faculty> remove(@PathVariable Long id) {
        Faculty deletedFaculty = service.remove(id);
        return ResponseEntity.ok(deletedFaculty);
    }

    @GetMapping("get-by-id/{id}")
    @Operation(summary = "Получение факультета по идентификатору")
    public ResponseEntity<Faculty> get(@PathVariable Long id) {
        Faculty faculty = service.get(id);
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("get-by-color/{color}")
    @Operation(summary = "Получение факультетов по цвету")
    public ResponseEntity<Collection<Faculty>> getByColor(@RequestParam String color) {
        Collection<Faculty> faculties = service.getByColor(color);
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("get-by-name-or-color/{name-color}")
    @Operation(summary = "Получение факультетов по имени или цвету")
    public ResponseEntity<Collection<Faculty>> getByNameOrColor(@RequestParam String name, @RequestParam String color) {
        Collection<Faculty> faculties = service.getByNameOrColor(name, color);
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("all")
    @Operation(summary = "Получение всех факультетов")
    public ResponseEntity<Collection<Faculty>> getAll() {
        Collection<Faculty> faculties = service.getAll();
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("student/{facultyId}")
    @Operation(summary = "Получение всех студентов по id факультета")
    public ResponseEntity<Collection<Student>> getStudentsByFaculty(@PathVariable Long facultyId) {
        Collection<Student> students = service.getStudents(facultyId);
        return ResponseEntity.ok(students);
    }
    @GetMapping("longest-name")
    @Operation(summary = "Получение самого длинного названия факультета")
    public String findLongestName() {
        return service.findLongestName();
    }

    @GetMapping("sumFormulaVar1")
    @Operation(summary = "Получение результата вычисления по формуле вариант1")
    public void getSumVar1() {
        long start = System.currentTimeMillis();
        int sum = Stream
                .iterate(1, a -> a+1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        long finish = System.currentTimeMillis();
        logger.info("Var 1 result: {} execution time: {}", sum, finish - start);
    }

    @GetMapping("sumFormulaVar2")
    @Operation(summary = "Получение результата вычисления по формуле вариант2")
    public void getSumVar2() {
        long start = System.currentTimeMillis();
        int sum = Stream
                .iterate(1, a -> a+1)
                .parallel()
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        long finish = System.currentTimeMillis();
        logger.info("Var 2 result: {} execution time: {}", sum, finish - start);
    }
    private final Logger logger = LoggerFactory.getLogger(FacultyController.class);
}

