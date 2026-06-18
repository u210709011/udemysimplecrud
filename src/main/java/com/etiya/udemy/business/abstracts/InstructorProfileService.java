package com.etiya.udemy.business.abstracts;

import com.etiya.udemy.business.dtos.requests.instructorprofile.CreateInstructorProfileRequest;
import com.etiya.udemy.business.dtos.responses.instructorprofile.InstructorProfileResponse;

import java.util.List;

public interface InstructorProfileService {
    InstructorProfileResponse add(CreateInstructorProfileRequest request);

    InstructorProfileResponse getById(Long id);

    List<InstructorProfileResponse> getAll();

    void delete(Long id);
}
