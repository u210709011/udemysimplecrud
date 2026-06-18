package com.etiya.udemy.business.concretes;

import com.etiya.udemy.business.abstracts.CourseService;
import com.etiya.udemy.business.dtos.requests.course.CreateCourseRequest;
import com.etiya.udemy.business.dtos.requests.course.UpdateCourseRequest;
import com.etiya.udemy.business.dtos.responses.course.CourseListItemResponse;
import com.etiya.udemy.business.dtos.responses.course.CreatedCourseResponse;
import com.etiya.udemy.business.dtos.responses.course.GetCourseResponse;
import com.etiya.udemy.business.mappers.CourseMapper;
import com.etiya.udemy.business.rules.CategoryBusinessRules;
import com.etiya.udemy.business.rules.CourseBusinessRules;
import com.etiya.udemy.business.rules.UserBusinessRules;
import com.etiya.udemy.dataAccess.abstracts.CourseRepository;
import com.etiya.udemy.entities.concretes.Course;
import com.etiya.udemy.entities.concretes.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CourseManager implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final CourseBusinessRules rules;
    private final CategoryBusinessRules categoryBusinessRules;
    private final UserBusinessRules userBusinessRules;

    public CourseManager(CourseRepository courseRepository,
                         CourseMapper courseMapper,
                         CourseBusinessRules rules,
                         CategoryBusinessRules categoryBusinessRules,
                         UserBusinessRules userBusinessRules) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.rules = rules;
        this.categoryBusinessRules = categoryBusinessRules;
        this.userBusinessRules = userBusinessRules;
    }

    @Override
    public CreatedCourseResponse add(CreateCourseRequest request) {
        rules.courseCodeCannotBeDuplicated(request.getCode());
        User instructor = userBusinessRules.userMustExist(request.getInstructorId());
        userBusinessRules.userMustBeTeacher(instructor);

        Course course = courseMapper.toEntity(request);
        course.setCategory(categoryBusinessRules.categoryMustExist(request.getCategoryId()));
        course.setInstructor(instructor);

        return courseMapper.toCreatedResponse(courseRepository.save(course));
    }

    @Override
    public CreatedCourseResponse update(UpdateCourseRequest request) {
        Course course = rules.courseMustExist(request.getId());

        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setPrice(request.getPrice());
        course.setCategory(categoryBusinessRules.categoryMustExist(request.getCategoryId()));

        return courseMapper.toCreatedResponse(courseRepository.save(course));
    }

    @Override
    public GetCourseResponse getById(Long id) {
        return courseMapper.toGetResponse(rules.courseMustExist(id));
    }

    @Override
    public List<CourseListItemResponse> getAll() {
        return courseMapper.toListItemResponseList(courseRepository.findAll());
    }

    @Override
    public void delete(Long id) {
        Course course = rules.courseMustExist(id);
        course.setActive(false);
        course.setDeletedDate(LocalDateTime.now());
        courseRepository.save(course);
    }
}
