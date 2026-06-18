package com.etiya.udemy.business.dtos.responses.user;

import com.etiya.udemy.entities.enums.ProfileName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatedUserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String mail;
    private LocalDate registrationDate;
    private ProfileName profileName;
}
