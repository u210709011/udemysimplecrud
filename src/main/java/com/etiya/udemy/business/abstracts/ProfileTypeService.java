package com.etiya.udemy.business.abstracts;

import com.etiya.udemy.business.dtos.requests.profiletype.CreateProfileTypeRequest;
import com.etiya.udemy.business.dtos.responses.profiletype.ProfileTypeResponse;

import java.util.List;

public interface ProfileTypeService {
    ProfileTypeResponse add(CreateProfileTypeRequest request);

    ProfileTypeResponse getById(Long id);

    List<ProfileTypeResponse> getAll();
}
