package com.etiya.udemy.business.abstracts;

import com.etiya.udemy.business.dtos.requests.certificate.CreateCertificateRequest;
import com.etiya.udemy.business.dtos.responses.certificate.CertificateResponse;

import java.util.List;

public interface CertificateService {
    CertificateResponse issue(CreateCertificateRequest request);

    CertificateResponse getById(Long id);

    List<CertificateResponse> getAll();

    void delete(Long id);
}
