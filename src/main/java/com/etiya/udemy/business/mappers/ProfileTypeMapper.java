package com.etiya.udemy.business.mappers;

import com.etiya.udemy.business.dtos.requests.profiletype.CreateProfileTypeRequest;
import com.etiya.udemy.business.dtos.responses.profiletype.ProfileTypeResponse;
import com.etiya.udemy.entities.concretes.ProfileType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = CentralMapperConfig.class)
public interface ProfileTypeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    ProfileType toEntity(CreateProfileTypeRequest request);

    ProfileTypeResponse toResponse(ProfileType profileType);

    List<ProfileTypeResponse> toResponseList(List<ProfileType> profileTypes);
}
