package com.etiya.udemy.business.rules;

import com.etiya.udemy.business.constants.Messages;
import com.etiya.udemy.core.crosscuttingconcerns.exceptions.types.BusinessException;
import com.etiya.udemy.dataAccess.abstracts.CourseRepository;
import com.etiya.udemy.entities.concretes.Course;
import org.springframework.stereotype.Service;

@Service
public class CourseBusinessRules {

    private final CourseRepository courseRepository;

    public CourseBusinessRules(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course courseMustExist(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new BusinessException(Messages.Course.NOT_FOUND));
    }

    public void courseCodeCannotBeDuplicated(String code) {
        if (courseRepository.existsByCode(code)) {
            throw new BusinessException(Messages.Course.CODE_ALREADY_EXISTS);
        }
    }
}
