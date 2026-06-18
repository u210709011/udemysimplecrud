package com.etiya.udemy.business.dtos.requests.profiletype;

import com.etiya.udemy.entities.enums.ProfileName;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProfileTypeRequest {

    @NotNull
    private ProfileName name;
}
