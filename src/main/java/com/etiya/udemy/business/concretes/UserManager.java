package com.etiya.udemy.business.concretes;

import com.etiya.udemy.business.abstracts.UserService;
import com.etiya.udemy.business.dtos.requests.user.CreateUserRequest;
import com.etiya.udemy.business.dtos.requests.user.UpdateUserRequest;
import com.etiya.udemy.business.dtos.responses.user.CreatedUserResponse;
import com.etiya.udemy.business.dtos.responses.user.GetUserResponse;
import com.etiya.udemy.business.dtos.responses.user.UserListItemResponse;
import com.etiya.udemy.business.mappers.UserMapper;
import com.etiya.udemy.business.rules.UserBusinessRules;
import com.etiya.udemy.dataAccess.abstracts.UserRepository;
import com.etiya.udemy.entities.concretes.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserManager implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserBusinessRules rules;

    public UserManager(UserRepository userRepository,
                       UserMapper userMapper,
                       UserBusinessRules rules) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.rules = rules;
    }

    @Override
    public CreatedUserResponse register(CreateUserRequest request) {
        rules.mailCannotBeDuplicated(request.getMail());

        User user = userMapper.toEntity(request);
        user.setProfileType(rules.profileTypeMustExist(request.getProfileId()));
        user.setRegistrationDate(LocalDate.now());

        return userMapper.toCreatedResponse(userRepository.save(user));
    }

    @Override
    public CreatedUserResponse update(UpdateUserRequest request) {
        User user = rules.userMustExist(request.getId());
        rules.mailCannotBeDuplicatedForOtherUser(request.getMail(), request.getId());

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setMail(request.getMail());

        return userMapper.toCreatedResponse(userRepository.save(user));
    }

    @Override
    public GetUserResponse getById(Long id) {
        return userMapper.toGetResponse(rules.userMustExist(id));
    }

    @Override
    public List<UserListItemResponse> getAll() {
        return userMapper.toListItemResponseList(userRepository.findAll());
    }

    @Override
    public void delete(Long id) {
        User user = rules.userMustExist(id);
        user.setActive(false);
        user.setDeletedDate(LocalDateTime.now());
        userRepository.save(user);
    }
}
