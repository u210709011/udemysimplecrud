package com.etiya.udemy.dataAccess.abstracts;

import com.etiya.udemy.entities.concretes.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsByUserIdAndCourseId(Long userId, Long courseId);
}
