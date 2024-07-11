package ru.hogwarts.school.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;


import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long>{
    Collection<Student> findByAgeBetween(Integer minAge, Integer maxAge);
    @Query(value = "SELECT COUNT(*) FROM student",
            nativeQuery = true)
    int getCount();

    @Query(value = "SELECT AVG (age) FROM student",
            nativeQuery = true)
    double getAvgAge();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5",
            nativeQuery = true)
    Collection<Student> getLastFiveOrderByIdDesc();
}

