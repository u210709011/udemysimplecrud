package com.etiya.udemy.api.controllers;

import com.etiya.udemy.business.abstracts.EnrollmentService;
import com.etiya.udemy.business.dtos.requests.enrollment.CreateEnrollmentRequest;
import com.etiya.udemy.business.dtos.responses.enrollment.EnrollmentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/enrollments")
@Tag(name = "Kayıtlar (Enrollment)", description = "Öğrencinin kursa kaydı. Ödeme yapılmadan kayıt olunamaz.")
public class EnrollmentsController {

    private final EnrollmentService enrollmentService;

    public EnrollmentsController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Kursa kaydol", description = "Öğrenci kursun ödemesini yapmış olmalı ve daha önce kaydolmamış olmalıdır. Eğitmen kendi kursuna kaydolamaz.")
    public EnrollmentResponse enroll(@Valid @RequestBody CreateEnrollmentRequest request) {
        return enrollmentService.enroll(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Kaydı id ile getir")
    public EnrollmentResponse getById(@PathVariable Long id) {
        return enrollmentService.getById(id);
    }

    @GetMapping
    @Operation(summary = "Tüm kayıtları listele")
    public List<EnrollmentResponse> getAll() {
        return enrollmentService.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Kaydı sil", description = "Soft-delete uygular.")
    public void delete(@PathVariable Long id) {
        enrollmentService.delete(id);
    }
}
