package com.etiya.udemy.business.dtos.responses.profiletype;

import com.etiya.udemy.entities.enums.ProfileName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileTypeResponse {
    private Long id;
    private ProfileName name;
}
