package com.etiya.udemy.business.abstracts;

import com.etiya.udemy.business.dtos.requests.user.CreateUserRequest;
import com.etiya.udemy.business.dtos.requests.user.UpdateUserRequest;
import com.etiya.udemy.business.dtos.responses.user.CreatedUserResponse;
import com.etiya.udemy.business.dtos.responses.user.GetUserResponse;
import com.etiya.udemy.business.dtos.responses.user.UserListItemResponse;

import java.util.List;

public interface UserService {
    CreatedUserResponse register(CreateUserRequest request);

    CreatedUserResponse update(UpdateUserRequest request);

    GetUserResponse getById(Long id);

    List<UserListItemResponse> getAll();

    void delete(Long id);
}
