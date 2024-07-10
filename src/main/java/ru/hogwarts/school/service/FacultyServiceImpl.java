package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.EntityNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty add(Faculty faculty) {
        logger.info("Was invoked method for add faculty");
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty get(Long id) {
        logger.info("Was invoked method for get faculty by id");
        //logger.error("There is not faculty with id = " + id);
        return facultyRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Faculty update(Faculty faculty) {
        logger.info("Was invoked method for update faculty");
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty remove(Long id) {
        logger.info("Was invoked method for delete faculty");
        Faculty faculty = get(id);
        facultyRepository.deleteById(id);
        return faculty;
    }

    @Override
    public Collection<Faculty> getByColor(String color) {
        logger.info("Was invoked method for get faculty by color");
        return getAll().stream()
                .filter(s -> s.getColor().equals(color))
                .collect(Collectors.toList());
    }


    @Override
    public Collection<Faculty> getByNameOrColor(String name, String color) {
        logger.info("Was invoked method for get faculty by name or color");
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    @Override
    public Collection<Student> getStudents(Long facultyId) {
        logger.info("Was invoked method for get students of faculty");
        return get(facultyId).getStudents();
    }

    @Override
    public Collection<Faculty> getAll() {
        logger.info("Was invoked method for get all faculties");
        return facultyRepository.findAll();
    }
}
