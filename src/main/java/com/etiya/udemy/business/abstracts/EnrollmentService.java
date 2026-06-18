package com.etiya.udemy.business.abstracts;

import com.etiya.udemy.business.dtos.requests.enrollment.CreateEnrollmentRequest;
import com.etiya.udemy.business.dtos.responses.enrollment.EnrollmentResponse;

import java.util.List;

public interface EnrollmentService {
    EnrollmentResponse enroll(CreateEnrollmentRequest request);

    EnrollmentResponse getById(Long id);

    List<EnrollmentResponse> getAll();

    void delete(Long id);
}
