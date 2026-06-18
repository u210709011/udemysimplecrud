package com.etiya.udemy.business.dtos.requests.certificate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCertificateRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long courseId;
}
