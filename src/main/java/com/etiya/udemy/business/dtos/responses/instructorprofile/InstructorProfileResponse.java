package com.etiya.udemy.business.dtos.responses.instructorprofile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstructorProfileResponse {
    private Long id;
    private Long userId;
    private String userFirstName;
    private String userLastName;
    private String description;
}
