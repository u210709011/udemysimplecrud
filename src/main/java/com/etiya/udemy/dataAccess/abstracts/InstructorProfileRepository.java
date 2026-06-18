package com.etiya.udemy.dataAccess.abstracts;

import com.etiya.udemy.entities.concretes.InstructorProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorProfileRepository extends JpaRepository<InstructorProfile, Long> {
    boolean existsByUserId(Long userId);
}
