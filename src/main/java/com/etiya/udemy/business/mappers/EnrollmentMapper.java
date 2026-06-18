package com.etiya.udemy.business.mappers;

import com.etiya.udemy.business.dtos.responses.enrollment.EnrollmentResponse;
import com.etiya.udemy.entities.concretes.UserCourseAssignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = CentralMapperConfig.class)
public interface EnrollmentMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "courseName", source = "course.name")
    EnrollmentResponse toResponse(UserCourseAssignment assignment);

    List<EnrollmentResponse> toResponseList(List<UserCourseAssignment> assignments);
}
