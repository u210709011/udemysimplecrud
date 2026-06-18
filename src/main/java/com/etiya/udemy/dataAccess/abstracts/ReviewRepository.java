package com.etiya.udemy.dataAccess.abstracts;

import com.etiya.udemy.entities.concretes.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByUserIdAndCourseId(Long userId, Long courseId);
}
