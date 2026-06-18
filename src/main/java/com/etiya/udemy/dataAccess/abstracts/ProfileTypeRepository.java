package com.etiya.udemy.dataAccess.abstracts;

import com.etiya.udemy.entities.concretes.ProfileType;
import com.etiya.udemy.entities.enums.ProfileName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileTypeRepository extends JpaRepository<ProfileType, Long> {
    Optional<ProfileType> findByName(ProfileName name);
}
