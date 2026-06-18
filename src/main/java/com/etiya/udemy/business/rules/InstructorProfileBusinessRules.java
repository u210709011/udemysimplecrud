package com.etiya.udemy.business.rules;

import com.etiya.udemy.business.constants.Messages;
import com.etiya.udemy.core.crosscuttingconcerns.exceptions.types.BusinessException;
import com.etiya.udemy.dataAccess.abstracts.InstructorProfileRepository;
import com.etiya.udemy.entities.concretes.InstructorProfile;
import org.springframework.stereotype.Service;

@Service
public class InstructorProfileBusinessRules {

    private final InstructorProfileRepository instructorProfileRepository;

    public InstructorProfileBusinessRules(InstructorProfileRepository instructorProfileRepository) {
        this.instructorProfileRepository = instructorProfileRepository;
    }

    public InstructorProfile instructorProfileMustExist(Long id) {
        return instructorProfileRepository.findById(id)
                .orElseThrow(() -> new BusinessException(Messages.InstructorProfile.NOT_FOUND));
    }

    public void userCannotHaveMultipleInstructorProfiles(Long userId) {
        if (instructorProfileRepository.existsByUserId(userId)) {
            throw new BusinessException(Messages.InstructorProfile.ALREADY_EXISTS);
        }
    }
}
