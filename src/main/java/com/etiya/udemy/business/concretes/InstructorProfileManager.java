package com.etiya.udemy.business.concretes;

import com.etiya.udemy.business.abstracts.InstructorProfileService;
import com.etiya.udemy.business.dtos.requests.instructorprofile.CreateInstructorProfileRequest;
import com.etiya.udemy.business.dtos.responses.instructorprofile.InstructorProfileResponse;
import com.etiya.udemy.business.mappers.InstructorProfileMapper;
import com.etiya.udemy.business.rules.InstructorProfileBusinessRules;
import com.etiya.udemy.business.rules.UserBusinessRules;
import com.etiya.udemy.dataAccess.abstracts.InstructorProfileRepository;
import com.etiya.udemy.entities.concretes.InstructorProfile;
import com.etiya.udemy.entities.concretes.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class InstructorProfileManager implements InstructorProfileService {

    private final InstructorProfileRepository instructorProfileRepository;
    private final InstructorProfileMapper instructorProfileMapper;
    private final InstructorProfileBusinessRules rules;
    private final UserBusinessRules userBusinessRules;

    public InstructorProfileManager(InstructorProfileRepository instructorProfileRepository,
                                    InstructorProfileMapper instructorProfileMapper,
                                    InstructorProfileBusinessRules rules,
                                    UserBusinessRules userBusinessRules) {
        this.instructorProfileRepository = instructorProfileRepository;
        this.instructorProfileMapper = instructorProfileMapper;
        this.rules = rules;
        this.userBusinessRules = userBusinessRules;
    }

    @Override
    public InstructorProfileResponse add(CreateInstructorProfileRequest request) {
        User user = userBusinessRules.userMustExist(request.getUserId());
        userBusinessRules.userMustBeTeacher(user);
        rules.userCannotHaveMultipleInstructorProfiles(request.getUserId());

        InstructorProfile profile = new InstructorProfile();
        profile.setUser(user);
        profile.setDescription(request.getDescription());

        return instructorProfileMapper.toResponse(instructorProfileRepository.save(profile));
    }

    @Override
    public InstructorProfileResponse getById(Long id) {
        return instructorProfileMapper.toResponse(rules.instructorProfileMustExist(id));
    }

    @Override
    public List<InstructorProfileResponse> getAll() {
        return instructorProfileMapper.toResponseList(instructorProfileRepository.findAll());
    }

    @Override
    public void delete(Long id) {
        InstructorProfile profile = rules.instructorProfileMustExist(id);
        profile.setActive(false);
        profile.setDeletedDate(LocalDateTime.now());
        instructorProfileRepository.save(profile);
    }
}
