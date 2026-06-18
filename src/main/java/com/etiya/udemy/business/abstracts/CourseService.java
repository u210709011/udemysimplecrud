package com.etiya.udemy.business.abstracts;

import com.etiya.udemy.business.dtos.requests.course.CreateCourseRequest;
import com.etiya.udemy.business.dtos.requests.course.UpdateCourseRequest;
import com.etiya.udemy.business.dtos.responses.course.CourseListItemResponse;
import com.etiya.udemy.business.dtos.responses.course.CreatedCourseResponse;
import com.etiya.udemy.business.dtos.responses.course.GetCourseResponse;

import java.util.List;

public interface CourseService {
    CreatedCourseResponse add(CreateCourseRequest request);

    CreatedCourseResponse update(UpdateCourseRequest request);

    GetCourseResponse getById(Long id);

    List<CourseListItemResponse> getAll();

    void delete(Long id);
}
