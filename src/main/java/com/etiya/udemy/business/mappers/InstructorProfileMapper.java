package com.etiya.udemy.business.mappers;

import com.etiya.udemy.business.dtos.responses.instructorprofile.InstructorProfileResponse;
import com.etiya.udemy.entities.concretes.InstructorProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = CentralMapperConfig.class)
public interface InstructorProfileMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userFirstName", source = "user.firstName")
    @Mapping(target = "userLastName", source = "user.lastName")
    InstructorProfileResponse toResponse(InstructorProfile instructorProfile);

    List<InstructorProfileResponse> toResponseList(List<InstructorProfile> instructorProfiles);
}
