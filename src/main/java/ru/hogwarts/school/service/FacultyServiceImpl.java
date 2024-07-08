package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.EntityNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty add(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty get(Long id) {
        return facultyRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Faculty update(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty remove(Long id) {
        Faculty faculty = get(id);
        facultyRepository.deleteById(id);
        return faculty;
    }

    @Override
    public Collection<Faculty> getByColor(String color) {
        return getAll().stream()
                .filter(s -> s.getColor().equals(color))
                .collect(Collectors.toList());
    }


    @Override
    public Collection<Faculty> getByNameOrColor(String name, String color) {
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    @Override
    public Collection<Student> getStudents(Long facultyId) {
        return get(facultyId).getStudents();
    }

    @Override
    public Collection<Faculty> getAll() {
        return facultyRepository.findAll();
    }
}
