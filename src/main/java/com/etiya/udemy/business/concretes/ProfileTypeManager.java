package com.etiya.udemy.business.concretes;

import com.etiya.udemy.business.abstracts.ProfileTypeService;
import com.etiya.udemy.business.constants.Messages;
import com.etiya.udemy.business.dtos.requests.profiletype.CreateProfileTypeRequest;
import com.etiya.udemy.business.dtos.responses.profiletype.ProfileTypeResponse;
import com.etiya.udemy.business.mappers.ProfileTypeMapper;
import com.etiya.udemy.core.crosscuttingconcerns.exceptions.types.BusinessException;
import com.etiya.udemy.dataAccess.abstracts.ProfileTypeRepository;
import com.etiya.udemy.entities.concretes.ProfileType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProfileTypeManager implements ProfileTypeService {

    private final ProfileTypeRepository profileTypeRepository;
    private final ProfileTypeMapper profileTypeMapper;

    public ProfileTypeManager(ProfileTypeRepository profileTypeRepository,
                              ProfileTypeMapper profileTypeMapper) {
        this.profileTypeRepository = profileTypeRepository;
        this.profileTypeMapper = profileTypeMapper;
    }

    @Override
    public ProfileTypeResponse add(CreateProfileTypeRequest request) {
        ProfileType profileType = profileTypeMapper.toEntity(request);
        return profileTypeMapper.toResponse(profileTypeRepository.save(profileType));
    }

    @Override
    public ProfileTypeResponse getById(Long id) {
        ProfileType profileType = profileTypeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(Messages.ProfileType.NOT_FOUND));
        return profileTypeMapper.toResponse(profileType);
    }

    @Override
    public List<ProfileTypeResponse> getAll() {
        return profileTypeMapper.toResponseList(profileTypeRepository.findAll());
    }
}
