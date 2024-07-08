package ru.hogwarts.school.repository;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;
import org.springframework.stereotype.Repository;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long>{
    Collection<Student> findByAgeBetween(Integer minAge, Integer maxAge);
}

