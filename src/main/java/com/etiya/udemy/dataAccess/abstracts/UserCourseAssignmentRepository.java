package com.etiya.udemy.dataAccess.abstracts;

import com.etiya.udemy.entities.concretes.UserCourseAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCourseAssignmentRepository extends JpaRepository<UserCourseAssignment, Long> {
    boolean existsByUserIdAndCourseId(Long userId, Long courseId);
}
