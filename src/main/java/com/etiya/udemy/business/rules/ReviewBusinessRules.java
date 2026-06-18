package com.etiya.udemy.business.rules;

import com.etiya.udemy.business.constants.Messages;
import com.etiya.udemy.core.crosscuttingconcerns.exceptions.types.BusinessException;
import com.etiya.udemy.dataAccess.abstracts.ReviewRepository;
import com.etiya.udemy.dataAccess.abstracts.UserCourseAssignmentRepository;
import com.etiya.udemy.entities.concretes.Course;
import com.etiya.udemy.entities.concretes.Review;
import org.springframework.stereotype.Service;

@Service
public class ReviewBusinessRules {

    private final ReviewRepository reviewRepository;
    private final UserCourseAssignmentRepository assignmentRepository;

    public ReviewBusinessRules(ReviewRepository reviewRepository,
                               UserCourseAssignmentRepository assignmentRepository) {
        this.reviewRepository = reviewRepository;
        this.assignmentRepository = assignmentRepository;
    }

    public Review reviewMustExist(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new BusinessException(Messages.Review.NOT_FOUND));
    }

    public void instructorCannotReviewOwnCourse(Long userId, Course course) {
        if (course.getInstructor() != null && course.getInstructor().getId().equals(userId)) {
            throw new BusinessException(Messages.Review.INSTRUCTOR_CANNOT_REVIEW_OWN_COURSE);
        }
    }

    public void userMustBeEnrolledToReview(Long userId, Long courseId) {
        if (!assignmentRepository.existsByUserIdAndCourseId(userId, courseId)) {
            throw new BusinessException(Messages.Review.MUST_BE_ENROLLED);
        }
    }

    public void userCannotReviewSameCourseTwice(Long userId, Long courseId) {
        if (reviewRepository.existsByUserIdAndCourseId(userId, courseId)) {
            throw new BusinessException(Messages.Review.ALREADY_REVIEWED);
        }
    }
}
