package com.etiya.udemy.dataAccess.abstracts;

import com.etiya.udemy.entities.concretes.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByCode(String code);
}
