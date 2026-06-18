package com.etiya.udemy.dataAccess.abstracts;

import com.etiya.udemy.entities.concretes.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    boolean existsByUserIdAndCourseId(Long userId, Long courseId);

    boolean existsByCertificateNo(String certificateNo);
}
