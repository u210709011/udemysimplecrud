package com.etiya.udemy.business.rules;

import com.etiya.udemy.business.constants.Messages;
import com.etiya.udemy.core.crosscuttingconcerns.exceptions.types.BusinessException;
import com.etiya.udemy.dataAccess.abstracts.PaymentRepository;
import com.etiya.udemy.dataAccess.abstracts.UserCourseAssignmentRepository;
import com.etiya.udemy.entities.concretes.Course;
import com.etiya.udemy.entities.concretes.UserCourseAssignment;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentBusinessRules {

    private final UserCourseAssignmentRepository assignmentRepository;
    private final PaymentRepository paymentRepository;

    public EnrollmentBusinessRules(UserCourseAssignmentRepository assignmentRepository,
                                   PaymentRepository paymentRepository) {
        this.assignmentRepository = assignmentRepository;
        this.paymentRepository = paymentRepository;
    }

    public UserCourseAssignment enrollmentMustExist(Long id) {
        return assignmentRepository.findById(id)
                .orElseThrow(() -> new BusinessException(Messages.Enrollment.NOT_FOUND));
    }

    public void studentCannotEnrollSameCourseTwice(Long userId, Long courseId) {
        if (assignmentRepository.existsByUserIdAndCourseId(userId, courseId)) {
            throw new BusinessException(Messages.Enrollment.ALREADY_ENROLLED);
        }
    }

    public void studentMustHavePaidForCourse(Long userId, Long courseId) {
        if (!paymentRepository.existsByUserIdAndCourseId(userId, courseId)) {
            throw new BusinessException(Messages.Enrollment.PAYMENT_REQUIRED);
        }
    }

    public void instructorCannotEnrollOwnCourse(Long userId, Course course) {
        if (course.getInstructor() != null && course.getInstructor().getId().equals(userId)) {
            throw new BusinessException(Messages.Enrollment.INSTRUCTOR_CANNOT_ENROLL_OWN_COURSE);
        }
    }
}
