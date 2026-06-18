package com.etiya.udemy.business.mappers;

import com.etiya.udemy.business.dtos.requests.course.CreateCourseRequest;
import com.etiya.udemy.business.dtos.responses.course.CourseListItemResponse;
import com.etiya.udemy.business.dtos.responses.course.CreatedCourseResponse;
import com.etiya.udemy.business.dtos.responses.course.GetCourseResponse;
import com.etiya.udemy.entities.concretes.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = CentralMapperConfig.class)
public interface CourseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "instructor", ignore = true)
    @Mapping(target = "enrollments", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    Course toEntity(CreateCourseRequest request);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "instructorId", source = "instructor.id")
    CreatedCourseResponse toCreatedResponse(Course course);

    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "instructorFirstName", source = "instructor.firstName")
    @Mapping(target = "instructorLastName", source = "instructor.lastName")
    GetCourseResponse toGetResponse(Course course);

    @Mapping(target = "categoryName", source = "category.name")
    CourseListItemResponse toListItemResponse(Course course);

    List<CourseListItemResponse> toListItemResponseList(List<Course> courses);
}
