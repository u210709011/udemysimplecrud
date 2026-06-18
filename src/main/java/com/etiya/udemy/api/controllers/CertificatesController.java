package com.etiya.udemy.api.controllers;

import com.etiya.udemy.business.abstracts.CertificateService;
import com.etiya.udemy.business.dtos.requests.certificate.CreateCertificateRequest;
import com.etiya.udemy.business.dtos.responses.certificate.CertificateResponse;
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
@RequestMapping("/api/v1/certificates")
@Tag(name = "Sertifikalar", description = "Kurs sertifikaları. Sertifika yalnızca kursa kayıtlı kullanıcıya bir kez verilir.")
public class CertificatesController {

    private final CertificateService certificateService;

    public CertificatesController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Sertifika oluştur", description = "Kullanıcı kursa kayıtlı olmalı ve daha önce sertifika almamış olmalıdır. Sertifika no otomatik üretilir.")
    public CertificateResponse issue(@Valid @RequestBody CreateCertificateRequest request) {
        return certificateService.issue(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Sertifikayı id ile getir")
    public CertificateResponse getById(@PathVariable Long id) {
        return certificateService.getById(id);
    }

    @GetMapping
    @Operation(summary = "Tüm sertifikaları listele")
    public List<CertificateResponse> getAll() {
        return certificateService.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Sertifikayı sil", description = "Soft-delete uygular.")
    public void delete(@PathVariable Long id) {
        certificateService.delete(id);
    }
}
