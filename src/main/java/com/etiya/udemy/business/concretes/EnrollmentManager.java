package com.etiya.udemy.business.concretes;

import com.etiya.udemy.business.abstracts.EnrollmentService;
import com.etiya.udemy.business.dtos.requests.enrollment.CreateEnrollmentRequest;
import com.etiya.udemy.business.dtos.responses.enrollment.EnrollmentResponse;
import com.etiya.udemy.business.mappers.EnrollmentMapper;
import com.etiya.udemy.business.rules.CourseBusinessRules;
import com.etiya.udemy.business.rules.EnrollmentBusinessRules;
import com.etiya.udemy.business.rules.UserBusinessRules;
import com.etiya.udemy.dataAccess.abstracts.UserCourseAssignmentRepository;
import com.etiya.udemy.entities.concretes.Course;
import com.etiya.udemy.entities.concretes.User;
import com.etiya.udemy.entities.concretes.UserCourseAssignment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class EnrollmentManager implements EnrollmentService {

    private final UserCourseAssignmentRepository assignmentRepository;
    private final EnrollmentMapper enrollmentMapper;
    private final EnrollmentBusinessRules rules;
    private final UserBusinessRules userBusinessRules;
    private final CourseBusinessRules courseBusinessRules;

    public EnrollmentManager(UserCourseAssignmentRepository assignmentRepository,
                             EnrollmentMapper enrollmentMapper,
                             EnrollmentBusinessRules rules,
                             UserBusinessRules userBusinessRules,
                             CourseBusinessRules courseBusinessRules) {
        this.assignmentRepository = assignmentRepository;
        this.enrollmentMapper = enrollmentMapper;
        this.rules = rules;
        this.userBusinessRules = userBusinessRules;
        this.courseBusinessRules = courseBusinessRules;
    }

    @Override
    public EnrollmentResponse enroll(CreateEnrollmentRequest request) {
        User user = userBusinessRules.userMustExist(request.getUserId());
        Course course = courseBusinessRules.courseMustExist(request.getCourseId());

        rules.instructorCannotEnrollOwnCourse(request.getUserId(), course);
        rules.studentCannotEnrollSameCourseTwice(request.getUserId(), request.getCourseId());
        rules.studentMustHavePaidForCourse(request.getUserId(), request.getCourseId());

        UserCourseAssignment assignment = new UserCourseAssignment();
        assignment.setUser(user);
        assignment.setCourse(course);
        assignment.setAssignmentDate(LocalDate.now());

        return enrollmentMapper.toResponse(assignmentRepository.save(assignment));
    }

    @Override
    public EnrollmentResponse getById(Long id) {
        return enrollmentMapper.toResponse(rules.enrollmentMustExist(id));
    }

    @Override
    public List<EnrollmentResponse> getAll() {
        return enrollmentMapper.toResponseList(assignmentRepository.findAll());
    }

    @Override
    public void delete(Long id) {
        UserCourseAssignment assignment = rules.enrollmentMustExist(id);
        assignment.setActive(false);
        assignment.setDeletedDate(LocalDateTime.now());
        assignmentRepository.save(assignment);
    }
}
