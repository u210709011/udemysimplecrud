package com.etiya.udemy.business.dtos.requests.instructorprofile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateInstructorProfileRequest {

    @NotNull
    private Long userId;

    @NotBlank
    @Size(max = 1000)
    private String description;
}
