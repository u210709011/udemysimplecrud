package com.etiya.udemy.business.dtos.responses.certificate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CertificateResponse {
    private Long id;
    private Long userId;
    private Long courseId;
    private String courseName;
    private String certificateNo;
    private LocalDate issueDate;
}
