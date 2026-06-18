package com.etiya.udemy.business.concretes;

import com.etiya.udemy.business.abstracts.ReviewService;
import com.etiya.udemy.business.dtos.requests.review.CreateReviewRequest;
import com.etiya.udemy.business.dtos.responses.review.ReviewResponse;
import com.etiya.udemy.business.mappers.ReviewMapper;
import com.etiya.udemy.business.rules.CourseBusinessRules;
import com.etiya.udemy.business.rules.ReviewBusinessRules;
import com.etiya.udemy.business.rules.UserBusinessRules;
import com.etiya.udemy.dataAccess.abstracts.ReviewRepository;
import com.etiya.udemy.entities.concretes.Course;
import com.etiya.udemy.entities.concretes.Review;
import com.etiya.udemy.entities.concretes.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ReviewManager implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final ReviewBusinessRules rules;
    private final UserBusinessRules userBusinessRules;
    private final CourseBusinessRules courseBusinessRules;

    public ReviewManager(ReviewRepository reviewRepository,
                         ReviewMapper reviewMapper,
                         ReviewBusinessRules rules,
                         UserBusinessRules userBusinessRules,
                         CourseBusinessRules courseBusinessRules) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.rules = rules;
        this.userBusinessRules = userBusinessRules;
        this.courseBusinessRules = courseBusinessRules;
    }

    @Override
    public ReviewResponse add(CreateReviewRequest request) {
        User user = userBusinessRules.userMustExist(request.getUserId());
        Course course = courseBusinessRules.courseMustExist(request.getCourseId());

        rules.instructorCannotReviewOwnCourse(request.getUserId(), course);
        rules.userMustBeEnrolledToReview(request.getUserId(), request.getCourseId());
        rules.userCannotReviewSameCourseTwice(request.getUserId(), request.getCourseId());

        Review review = new Review();
        review.setUser(user);
        review.setCourse(course);
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setReviewDate(LocalDate.now());

        return reviewMapper.toResponse(reviewRepository.save(review));
    }

    @Override
    public ReviewResponse getById(Long id) {
        return reviewMapper.toResponse(rules.reviewMustExist(id));
    }

    @Override
    public List<ReviewResponse> getAll() {
        return reviewMapper.toResponseList(reviewRepository.findAll());
    }

    @Override
    public void delete(Long id) {
        Review review = rules.reviewMustExist(id);
        review.setActive(false);
        review.setDeletedDate(LocalDateTime.now());
        reviewRepository.save(review);
    }
}
