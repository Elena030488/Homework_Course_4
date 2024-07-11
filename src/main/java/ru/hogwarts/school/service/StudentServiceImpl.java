package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.EntityNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student add(Student student) {
        logger.info("Was invoked method for add student");
        return studentRepository.save(student);
    }

    @Override
    public Student get(Long id) {
        logger.info("Was invoked method for get student by id");
        return studentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("There is not student with id = " + id);
                    return new EntityNotFoundException();
                });
    }

    @Override
    public Student update(Student student) {
        logger.info("Was invoked method for update student");
        return studentRepository.save(student);
    }

    @Override
    public Student remove(Long id) {
        logger.info("Was invoked method for remove student");
        Student student = get(id);
        studentRepository.deleteById(id);
        return student;
    }

    @Override
    public Collection<Student> getByAge(Integer minAge, Integer maxAge) {
        logger.info("Was invoked method for get student by age");
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    @Override
    public Faculty getFacultyByStudent(Long studentId) {
        logger.info("Was invoked method for get faculty of student");
        return get(studentId).getFaculty();
    }

    @Override
    public Collection<Student> getAll() {
        logger.info("Was invoked method for get all students");
        return studentRepository.findAll();
    }
    @Override
    public int getCount() {
        logger.info("Was invoked method for get count of students");
        return studentRepository.getCount();
    }
    @Override
    public double getAvgAge() {
        logger.info("Was invoked method for get average age of students");
        return studentRepository.getAvgAge();
    }
    @Override
    public Collection<Student>getLastFive() {
        logger.info("Was invoked method for get last five students");
        return studentRepository.getLastFiveOrderByIdDesc();
    }
    @Override
    public Collection<String> getStudentsNamesStartWithA() {
        logger.info("Was invoked method for get students names start with A");
        String firstSymbol= "A";
        return studentRepository.findAll().stream()
                .map(student ->student.getName().toUpperCase())
                .filter(name ->name.startsWith(firstSymbol))
                .sorted()
                .collect(Collectors.toList());
    }
    @Override
    public Double getAvgAgeWithStream() {
        logger.info("Was invoked method for get average age of students with stream");
        return studentRepository.findAll().stream()
                .mapToDouble(Student::getAge)
                .average()
                .orElse(0);
    }
}