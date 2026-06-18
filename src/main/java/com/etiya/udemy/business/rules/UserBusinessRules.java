package com.etiya.udemy.business.rules;

import com.etiya.udemy.business.constants.Messages;
import com.etiya.udemy.core.crosscuttingconcerns.exceptions.types.BusinessException;
import com.etiya.udemy.dataAccess.abstracts.ProfileTypeRepository;
import com.etiya.udemy.dataAccess.abstracts.UserRepository;
import com.etiya.udemy.entities.concretes.ProfileType;
import com.etiya.udemy.entities.concretes.User;
import com.etiya.udemy.entities.enums.ProfileName;
import org.springframework.stereotype.Service;

@Service
public class UserBusinessRules {

    private final UserRepository userRepository;
    private final ProfileTypeRepository profileTypeRepository;

    public UserBusinessRules(UserRepository userRepository,
                             ProfileTypeRepository profileTypeRepository) {
        this.userRepository = userRepository;
        this.profileTypeRepository = profileTypeRepository;
    }

    public User userMustExist(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(Messages.User.NOT_FOUND));
    }

    public ProfileType profileTypeMustExist(Long profileId) {
        return profileTypeRepository.findById(profileId)
                .orElseThrow(() -> new BusinessException(Messages.ProfileType.NOT_FOUND));
    }

    public void mailCannotBeDuplicated(String mail) {
        if (userRepository.existsByMail(mail)) {
            throw new BusinessException(Messages.User.MAIL_ALREADY_EXISTS);
        }
    }

    public void mailCannotBeDuplicatedForOtherUser(String mail, Long userId) {
        userRepository.findByMail(mail).ifPresent(existing -> {
            if (!existing.getId().equals(userId)) {
                throw new BusinessException(Messages.User.MAIL_ALREADY_EXISTS);
            }
        });
    }

    public void userMustBeTeacher(User user) {
        if (user.getProfileType() == null || user.getProfileType().getName() != ProfileName.TEACHER) {
            throw new BusinessException(Messages.User.MUST_BE_TEACHER);
        }
    }
}
