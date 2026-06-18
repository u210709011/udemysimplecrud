package com.etiya.udemy.business.mappers;

import com.etiya.udemy.business.dtos.requests.user.CreateUserRequest;
import com.etiya.udemy.business.dtos.responses.user.CreatedUserResponse;
import com.etiya.udemy.business.dtos.responses.user.GetUserResponse;
import com.etiya.udemy.business.dtos.responses.user.UserListItemResponse;
import com.etiya.udemy.entities.concretes.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = CentralMapperConfig.class)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profileType", ignore = true)
    @Mapping(target = "instructorProfile", ignore = true)
    @Mapping(target = "instructedCourses", ignore = true)
    @Mapping(target = "enrollments", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    User toEntity(CreateUserRequest request);

    @Mapping(target = "profileName", source = "profileType.name")
    CreatedUserResponse toCreatedResponse(User user);

    @Mapping(target = "profileName", source = "profileType.name")
    GetUserResponse toGetResponse(User user);

    @Mapping(target = "profileName", source = "profileType.name")
    UserListItemResponse toListItemResponse(User user);

    List<UserListItemResponse> toListItemResponseList(List<User> users);
}
